package studyon.app.layer.domain.lecture_review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 리뷰 서비스 구현 클래스
 * @version 1.0
 * @author khj00
 */

@Service
@Transactional
@RequiredArgsConstructor
public class LectureReviewServiceImpl implements LectureReviewService {

    private final LectureReviewRepository lectureReviewRepository;
    private final LectureRepository lectureRepository;

    /**
     * 특정 선생님의 모든 강의 리뷰를 최신순으로 조회
     *
     * @param teacherId 선생님 ID
     * @param count
     * @return 리뷰 DTO 리스트
     */
    @Override
    public List<LectureReviewDTO.Read> readTeacherReviews(Long teacherId, int count) {
        // [1] 리스팅(페이징?) 개수 지정
        Pageable pageable = PageRequest.of(0, count);
        // [2] 선생님 ID를 기반으로 최신순 리뷰 정렬
        return lectureReviewRepository.findRecentReviewsByTeacherId(teacherId, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }
    /**
     * 특정 과목의 모든 강의 리뷰를 최신순으로 조회
     *
     * @param subject  특정 과목
     * @param count 카운트 변수
     * @return 리뷰 DTO 리스트
     */
    @Override
    public List<LectureReviewDTO.Read> readSubjectReviews(Subject subject, int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 과목 기반으로 최신순 리뷰 정렬
        return lectureReviewRepository.findRecentReviewsBySubject(subject, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }
}
