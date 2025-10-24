package studyon.app.infra.file;

import studyon.app.layer.domain.file.FileDTO;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-20) : kcw97 임시파일 저장/삭제 가능 추가. FileType 추가
 */

/**
 * 파일의 저장, 삭제, 다운로드 메소드 처리
 * @version 1.1
 * @author kcw97
 */
public interface FileManager {

    /**
     * 파일 업로드
     * @param rq 파일 업로드 요청 DTO
     */
    void upload(FileDTO.Upload rq);

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

}
