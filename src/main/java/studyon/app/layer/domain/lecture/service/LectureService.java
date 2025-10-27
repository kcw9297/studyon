package studyon.app.layer.domain.lecture.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author khj00
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
    LectureDTO.ReadLectureInfo readLectureInfo(Long lectureId,Long teacherId);
    void updateThumbnail(Long teacherId, Long lectureId, MultipartFile file);
    String getLectureThumbnailPath(Long lectureId);
}
