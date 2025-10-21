package studyon.app.layer.domain.lecture.service;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.LectureDTO;

import java.util.List;

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureService {
    /** 강의 평점 업데이트 로직
     * @param lectureId
     * @return 평점 계산 결과
     */
    Double updateAverageRatings(Long lectureId);

    /** 과목별 최근 강의 목록 조회 로직
     * @param subject 해당 과목
     * @return 해당 과목의 최근 강의 목록
     */
    List<LectureDTO.Read> readRecentLectures(Subject subject, int count);

    /** 과목별 BEST 강의 목록 조회 로직
     * @param subject 해당 과목 
     * @return 해당 과목의 BEST 강의 목록
     */
    
    List<LectureDTO.Read> readBestLectures(Subject subject, int count);
}
