package studyon.app.layer.domain.lecture.service;

import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.List;
import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-27) : phj : 리뷰 퍼센트 계산 - 강의페이지 추가
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.1
 * @author phj
 */

public interface LectureService {
    /** 과목별 최근 강의 목록 조회 로직
     * @param subject 해당 과목
     * @param count 정렬용 변수
     * @return 해당 과목의 최근 강의 목록
     */
    List<LectureDTO.Read> readRecentLectures(Subject subject, int count);

    /** 과목별 BEST 강의 목록 조회 로직
     * @param subject 해당 과목
     * @param count 정렬용 변수
     * @return 해당 과목의 BEST 강의 목록
     */
    List<LectureDTO.Read> readBestLectures(Subject subject, int count);


    /** 최신순 강의 목록 조회 로직 (홈화면)
     * @param count 정렬용 변수
     * @return 전체 최신순 강의 목록
     */
    List<LectureDTO.Read> readAllRecentLectures(int count);


    /** 인기순(수강생수 기준) 강의 목록 조회 로직 (홈화면)
     * @param count 정렬용 변수
     * @return 전체 인기순 강의 목록
     */
    List<LectureDTO.Read> readAllPopularLectures(int count);

    /**
     * 선생님 담당 강의 조회
     * @return 해당 선생님 강의 리스트
     */
    List<LectureDTO.Read> readBestLectures(Long teacherId, int count);

    /**
     * 선생님 최신 강의 조회
     * @return 해당 선생님 최신 강의 리스트
     */
    List<LectureDTO.Read> readRecentLectures(Long teacherId, int count);
    LectureDTO.Register registerLecture(LectureDTO.Register dto, MemberProfile profile);

    /* 리뷰 퍼센트 계산 - 강의페이지 */
    Map<Integer, Double> getRatingPercentage(Long lectureId);
}
