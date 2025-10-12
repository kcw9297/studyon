package studyon.app.common.infra.file;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.domain.file.FileDTO;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 파일의 저장, 삭제, 다운로드 메소드 처리
 * @version 1.0
 * @author kcw97
 */
public interface FileManager {

    /**
     * 파일 업로드
     * @param file 업로드 대상 파일
     * @param entityId 업로드 파일이 속하는 엔티티 고유번호
     * @param entity 업로드 파일이 속하는 엔티티 타입
     * @return 업로드 파일 DTO 반환
     */
    FileDTO.Upload upload(MultipartFile file, Long entityId, Entity entity);

    /**
     * 파일 다운로드
     * @param storeName 저장 파일명
     * @param entity 저장된 파일이 속하는 엔티티 타입
     * @return 파일 바이트 문자열 반환
     */
    byte[] download(String storeName, Entity entity);


    /**
     * 파일 삭제
     * @param storeName 저장 파일명
     * @param entity 저장된 파일이 속하는 엔티티 타입
     */
    void remove(String storeName, Entity entity);
}
