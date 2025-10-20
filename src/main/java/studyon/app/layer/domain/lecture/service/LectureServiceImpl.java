package studyon.app.layer.domain.lecture.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;

    /** 강의 평점 업데이트 로직
     * @param lectureId
     * @return 평점 계산 결과
     */

    @Override
    public Double updateLectureAverageRatings(Long lectureId) {
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
}
