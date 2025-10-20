package studyon.app.infra.file;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
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
     * @param file     업로드 대상 파일
     * @param entityId 업로드 파일이 속하는 엔티티 고유번호
     * @param entity   업로드 파일이 속하는 엔티티 타입
     * @param fileType 파일 유형 (썸네일, 직접업로드, 프로필 이미지, 에디터, ...)
     * @return 업로드 요청 파일 DTO
     */
    FileDTO.Upload upload(MultipartFile file, Long entityId, Entity entity, FileType fileType);

    /**
     * 임시 파일 저장
     * @param file 업로드 대상 파일
     * @return 업로드에 성공한 파일명
     */
    String uploadToTemp(MultipartFile file);

    /**
     * 임시 파일 삭제
     * @param fileName 삭제 대상 파일명
     */
    void removeTemp(String fileName);

    /**
     * 임시파일을 실제 entity 파일 저장소에 전달 (temp 내의 파일은 삭제)
     * @param fileName 업로드 대상 파일명
     * @param entityId 업로드 파일이 속하는 엔티티 고유번호
     * @param entity 업로드 파일이 속하는 엔티티 타입
     * @param fileType 파일 유형 (썸네일, 직접업로드, 프로필 이미지, 에디터, ...)
     * @return 업로드 파일
     */
    String copyTempToEntity(String fileName, Long entityId, Entity entity, FileType fileType);

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
