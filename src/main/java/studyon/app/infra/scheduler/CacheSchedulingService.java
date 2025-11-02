package studyon.app.infra.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.EditorCacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.domain.editor.EditorCache;
import studyon.app.layer.domain.file.repository.FileRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Transactional
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheSchedulingService {

    private final EditorCacheManager editorCacheManager;
    private final FileManager fileManager;
    private final FileRepository fileRepository;

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void removeOrphanEditorCaches() {

        // [1] 고아 상태의 에디터 캐시정보 일괄 조회
        List<EditorCache> orphans =
                editorCacheManager.getAndRemoveAllOrphanCache(EditorCache.class);

        // 로깅
        //log.warn("Removing orphan caches: {}", orphans);

        // [2] 내부의 업로드했던 파일 모두 삭제
        orphans.stream()
                .map(EditorCache::getUploadFiles)
                .flatMap(List::stream)
                .forEach(fileDto -> {
                    try {
                        fileManager.remove(fileDto.getFilePath());
                    } catch (Exception e) {
                        log.error(StrUtils.createLogStr(this.getClass(), "특정 파일 삭제 실패! fileDto = %s, 오류 : %s".formatted(fileDto, e.getMessage())));
                    }
                });

        // [3] 내부의 "삭제된 파일" 일괄 삭제
        orphans.stream()
                .map(EditorCache::getRemoveFiles)
                .flatMap(List::stream)
                .forEach(fileDto -> {
                    try {
                        fileRepository.deleteById(fileDto.getFileId());
                        fileManager.remove(fileDto.getFilePath());
                    } catch (Exception e) {
                        log.error(StrUtils.createLogStr(this.getClass(), "특정 파일 삭제 실패! fileDto = %s, 오류 : %s".formatted(fileDto, e.getMessage())));
                    }
                });
    }


/*
 @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)

    // yaml 상수도 가져올 수 있음
    @Scheduled(fixedDelayString = "${scheduler.fixed.delay}")
    public void taskWithProperty() {
        // application.properties: scheduler.fixed.delay=10000
    }

    // 앱 시작 후 10초뒤 실행. 그 이후 5초마다 실행
    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void executeWithInitialDelay() {
        log.info("초기 지연 후 실행: {}", LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void taskInSeconds() {
        System.out.println("10초 간격");
    }

    // 분 단위
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void taskInMinutes() {
        System.out.println("5분마다 실행");
    }

    // 시간 단위
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void taskInHours() {
        System.out.println("매시간 실행");
    }

    // initialDelay도 같은 단위 적용
    @Scheduled(initialDelay = 30, fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
    public void taskWithTimeUnit() {
        System.out.println("30초 후 시작, 60초 간격");
    }

    // 평일(월~금) 오전 10시
    @Scheduled(cron = "0 0 10 * * MON-FRI")
    public void runOnWeekdays() { }



    // 매일 자정 (00:00:00)
    @Scheduled(cron = "0 0 0 * * *")
    public void runAtMidnight() { }



    // 매년 1월 1일 자정 (새해 첫날)
    @Scheduled(cron = "0 0 0 1 1 *")
    public void runOnNewYear() { }

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void taskInSeoulTime() {
        System.out.println("서울 시간 기준 오전 9시");
    }
     */


}
