package studyon.app.layer.domain.lecture_review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;

import java.util.List;
import java.util.Optional;
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

    @Override
    public List<LectureReviewDTO.Read> getReviewsByTeacherId(Long teacherId) {
        List<LectureReview> reviews = lectureReviewRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId);

        return reviews.stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
}
