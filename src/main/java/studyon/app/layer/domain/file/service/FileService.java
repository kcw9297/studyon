package studyon.app.layer.domain.file.service;

import studyon.app.layer.domain.file.FileDTO;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 파일 핵심 비즈니스 로직 처리
 * @version 1.0
 * @author kcw97
 */
public interface FileService {

    /**
     * 단일 파일정보 조회
     * @param fileId 대상 파일번호 (PK)
     * @return 조회된 파일 정보 반환
     */
    FileDTO.Read read(Long fileId);

    /**
     * 전체 파일 조회 (테스트용)
     * @return 조회된 전체 파일 정보 리스트 반환
     */
    List<FileDTO.Read> readList();

}
