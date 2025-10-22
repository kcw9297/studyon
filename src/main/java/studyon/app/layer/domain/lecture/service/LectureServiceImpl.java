package studyon.app.layer.domain.lecture.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;

import java.util.List;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 서비스 인터페이스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;

    /** 강의 평점 업데이트 로직
     * @param lectureId 선생님 ID
     * @return 평점 계산 결과
     */
    @Override
    public Double updateAverageRatings(Long lectureId) {
        // [1] 리뷰 DTO 리스트 조회
        List<LectureReviewDTO.Read> reviews = lectureRepository.findByLectureId(lectureId)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());

        // [2] 평균 평점 계산
        Double avgRating = reviews.isEmpty()
                ? 0.0
                : reviews.stream().mapToInt(LectureReviewDTO.Read::getRating)
                .average()
                .orElse(0.0);

        // [3] 소수점 둘째 자리 반올림 후 반환
        return Math.round(avgRating * 100.0) / 100.0;
    }


    /** 최근 강의 리스트 불러오는 메소드
     * @param subject 과목
     * @param count 카운트용 변수
     * @return 최근 강의 리스트
     */
    @Override
    public List<LectureDTO.Read> readRecentLectures(Subject subject, int count) {
        Pageable pageable = PageRequest.of(0, count);
        return lectureRepository.findRecentLecturesBySubject(subject, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }

    /** BEST 강의 리스트 불러오는 메소드
     * @param subject 과목
     * @param count 카운트용 변수
     * @return BEST 강의 리스트
     */

    @Override
    public List<LectureDTO.Read> readBestLectures(Subject subject, int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 과목 기반으로 BEST 리뷰 정렬
        return lectureRepository.findBestLecturesBySubject(subject, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }

    /** 최신순 강의 목록 조회 로직 (홈화면)
     * @param count 정렬용 변수
     * @return 최신순(강의 목록 구별 X) 강의 목록
     */
    @Override
    public List<LectureDTO.Read> readAllRecentLectures(int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 모든 강의 중 카운트만큼 최신순 정렬
        return lectureRepository.findAllByOrderByPublishDateDesc(pageable)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
    /** 인기순(수강생수 기준) 강의 목록 조회 로직 (홈화면)
     * @param count 정렬용 변수
     * @return 전체 인기순 강의 목록
     */
    @Override
    public List<LectureDTO.Read> readAllPopularLectures(int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 모든 강의 중 카운트만큼 인기순 정렬
        return lectureRepository.findAllByOrderByTotalStudentsDesc(pageable)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
}
