package studyon.app.layer.domain.lecture.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.lecture.LectureDTO;

import java.util.*;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 *  ▶ ver 1.1 (2025-10-27) : phj03 : 리뷰 퍼센트 계산 - 강의페이지 추가
 *  ▶ ver 1.2 (2025-10-31) : kcw97 : 강의 상태변경 메소드 추가
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.1
 * @author phj
 */

public interface LectureService {

    /**
     * 강의 검색
     * @param rq 강의 검색 요청
     * @param prq 페이징 요청
     * @return 페이징된 강의 리스트
     */
    Page.Response<LectureDTO.Read> readPagedList(LectureDTO.Search rq, Page.Request prq);


    /**
     * 최근 검색어 조회
     * @param memberId 대상 회원번호
     * @return 최근 문자열 리스트
     */
    List<String> readRecentKeywords(Long memberId);


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

    Long create(LectureDTO.Create dto);

    LectureDTO.ReadLectureInfo readLectureInfo(Long lectureId,Long teacherId);

    void updateThumbnail(Long teacherId, Long lectureId, MultipartFile file);

    String getLectureThumbnailPath(Long lectureId);

    /* 리뷰 퍼센트 계산 - 강의페이지 */
    Map<Integer, Double> getRatingPercentage(Long lectureId);

    /* 알고리즘용 특정 과목 Best 리스트 */
    List<LectureDTO.Read> readBestLecturesBySubject(String subject, int count);
    List<LectureDTO.Read> readBestLecturesByTeacher(Long teacherId, int count);

    void startSale(Long lectureId);

    void stopSale(Long lectureId);

    void pending(Long lectureId);

    void register(Long lectureId);

    void reject(Long lectureId, String rejectReason);

    /* 관리자 강의 통계용 메소드들 */
    
    Map<String, Long> readLectureCountBySubject();

    Map<String, Long> readLectureCountByDifficulty();

    Map<String, Long> readLectureCountByStatus();

    List<LectureDTO.Read> readTopRatedLectures(int count);
}
