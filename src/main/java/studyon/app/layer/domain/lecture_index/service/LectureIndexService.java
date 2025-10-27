package studyon.app.layer.domain.lecture_index.service;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-16) : khj00 최초 작성
 */

import studyon.app.layer.domain.lecture_index.LectureIndexDTO;

import java.util.List;

/**
 * 강의 목차 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */
public interface LectureIndexService {
    List<LectureIndexDTO.Read> readAllByLectureId(Long lectureId, Long teacherId);
    void createIndex(Long lectureId, Long teacherId, LectureIndexDTO.Write dto);
    void updateIndexes(Long lectureId, Long teacherId, List<LectureIndexDTO.Edit> dtos);
    void deleteIndex(Long lectureIndexId, Long teacherId);
}
