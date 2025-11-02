package studyon.app.infra.file;

import org.springframework.web.multipart.MultipartFile;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-23) : kcw97 더 이상 매니저 클래스에서 DTO를 생성하지 않음 (의존 제거)
 */

/**
 * 파일의 저장, 삭제, 다운로드 메소드 처리
 * @version 1.1
 * @author kcw97
 */
public interface FileManager {

    /**
     * 파일 업로드
     * @param file       저장 대상 파일
     * @param storeName  저장 파일명
     * @param entityName 저장된 파일이 속하는 엔티티 타입명
     * @return 저장된 파일 경로
     */
    String upload(MultipartFile file, String storeName, String entityName);

    /**
     * 파일 다운로드
     *
     * @param storeName  저장 파일명
     * @param entityName 저장된 파일이 속하는 엔티티 타입명
     * @return 파일 바이트 문자열 반환
     */
    byte[] download(String storeName, String entityName);


    /**
     * 파일 삭제
     * @param storeName  저장 파일명
     * @param entityName 저장된 파일이 속하는 엔티티 타입명
     */
    void remove(String storeName, String entityName);

    /**
     * 파일 삭제
     * @param filePath 저장 파일 주소
     */
    void remove(String filePath);


    /**
     * 비디오 파일의 FullUrl 제공
     * @param fileUrl 파일 url
     * @return 도메인이 포함된 fileUrl
     */
    String getFullVideoUrl(String fileUrl);

}
