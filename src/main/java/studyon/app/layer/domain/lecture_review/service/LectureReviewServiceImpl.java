package studyon.app.layer.domain.lecture_review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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
    /**
     * 특정 선생님의 모든 강의 리뷰를 최신순으로 조회
     * @param teacherId 선생님 ID
     * @return 리뷰 DTO 리스트
     */

    @Override
    public List<LectureReviewDTO.Read> getReviewsByTeacherId(Long teacherId, Pageable pageable) {
        // [1] 선생님 ID를 기반으로 최신순 리뷰 정렬
        return lectureReviewRepository.findRecentReviewsByTeacherId(teacherId, pageable)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
}
