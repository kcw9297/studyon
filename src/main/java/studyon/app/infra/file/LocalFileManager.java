package studyon.app.infra.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Env;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.ManagerException;
import studyon.app.common.utils.StrUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-20) : kcw97 임시파일 저장/삭제 가능 추가. FileType 추가
 *  ▶ ver 1.2 (2025-10-24) : kcw97 파일에서 DTO 생성로직 삭제 (로직 일부 변경)
 */

/**
 * 파일의 저장, 삭제, 다운로드 메소드 처리
 * @version 1.2
 * @author kcw97
 */

@Profile(Env.PROFILE_LOCAL)
@Slf4j
@Component
public class LocalFileManager implements FileManager {

    @Value("${file.dir}") // properties(yml) 파일 내 프로퍼티 값을 직접 사용 (빈 주입 시기에 같이 삽입)
    private String fileDir;

    @Value("${file.domain}") // properties(yml) 파일 내 프로퍼티 값을 직접 사용 (빈 주입 시기에 같이 삽입)
    private String fileDomain;


    @Override
    public String upload(MultipartFile file, String storeName, String entityName) {

        try {

            // [1] 저장 파일 디렉토리 확인 (없을 시 생성)
            String uploadPath = "%s/%s/".formatted(fileDir, entityName);
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            // [2] 파일 검증
            if (Objects.isNull(file) || file.isEmpty()) {
                log.error(StrUtils.createLogStr(this.getClass(), "파일이 존재하지 않아 업로드 실패!"));
                throw new ManagerException(AppStatus.FILE_NOT_FOUND); // 파일이 존재하지 않으면 예외 발생
            }

            // [3] 파일 업로드 & 파일 업로드 정보 DTO 생성 후 반환
            file.transferTo(new File("%s/%s".formatted(uploadPath, storeName))); // 파일 업로드
            return "%s/%s/%s".formatted(fileDomain, entityName, storeName); // 저장한 파일 경로 반환

        } catch (ManagerException e) {
            throw e;

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "로컬 스토리지 파일 업로드에 실패! 오류 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }


    @Override
    public byte[] download(String storeName, String entityName) {

        try {
            // [1] 파일 저장 경로
            String filePath = "%s/%s/%s".formatted(fileDir, entityName, storeName);

            // [2] Path 생성
            Path path = Paths.get(filePath);

            // [3] 파일 바이트 문자열 반환
            return Files.readAllBytes(path);

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "로컬 스토리지 파일 업로드에 실패! 오류 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }


    @Override
    public void remove(String storeName, String entityName) {

        try {
            // [1] 파일 저장 경로
            String filePath = "%s/%s/%s".formatted(fileDir, entityName, storeName);
            Path path = Paths.get(filePath);

            // [2] 파일이 존재하는 경우 파일 삭제
            if (Files.exists(path)) Files.delete(path);

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "로컬 스토리지 파일 업로드에 실패! 오류 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }

    @Override
    public void remove(String filePath) {

        try {
            // [1] 파일 저장 경로
            Path path = Paths.get("%s/%s".formatted(fileDir, filePath));

            // [2] 파일이 존재하는 경우 파일 삭제
            if (Files.exists(path)) Files.delete(path);

        } catch (Exception e) {
            log.error(StrUtils.createLogStr(this.getClass(), "로컬 스토리지 파일 업로드에 실패! 오류 : %s".formatted(e.getMessage())));
            throw new ManagerException(AppStatus.UTILS_LOGIC_FAILED, e);
        }
    }


}
