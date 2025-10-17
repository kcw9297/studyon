package studyon.app.layer.domain.lecture.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.Objects;

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

    @Override
    public void updateLectureAverageRatings() {
        List<Object[]> results = lectureRepository.findLectureAverageRatings();

        for (Object[] row : results) {
            Long lectureId = (Long) row[0];
            Double avgRating = ((Number) row[1]).doubleValue();

            lectureRepository.findById(lectureId).ifPresent(lecture -> {
                lecture.updateAverageRate(avgRating);
            });
        }
    }
}
