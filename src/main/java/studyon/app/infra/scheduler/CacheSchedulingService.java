package studyon.app.infra.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.Entity;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.domain.test.TestCache;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Transactional
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheSchedulingService {

    private final FileManager fileManager;
    private final CacheManager cacheManager;

    /**
     * 주기적으로 LectureQuestion Cache 데이터 삭제 (고아 파일 삭제)
     */
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS) // 간격 : 10초
    public void deleteLectureQuestionBackupCache() {

        List<TestCache> allBackUp =
                cacheManager.getAndRemoveAllBackup(Entity.LECTURE_QUESTION.name(), TestCache.class);

        //log.info("[SchedulingService] - allBackUp = {}", allBackUp);

        // [2] 백업 캐시 & 고아(orphan) 파일 삭제
        allBackUp.stream()
                .map(TestCache::getUploadedImages)
                .flatMap(List::stream)
                .forEach(dto -> fileManager.remove(dto.getStoreName(), dto.getEntity().getName()));
    }


/*

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
