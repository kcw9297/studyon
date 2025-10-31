package studyon.app.layer.domain.lecture_review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Subject;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_review.LectureReview;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

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
    private final TeacherRepository teacherRepository;
    private final MemberRepository memberRepository;


    /**
     * 특정 선생님의 모든 강의 리뷰를 최신순으로 조회
     *
     * @param teacherId 선생님 ID
     * @param count
     * @return 리뷰 DTO 리스트
     */
    @Override
    public List<LectureReviewDTO.Read> readTeacherReviews(Long teacherId, int count) {
        // [1] 리스팅 카운트용 변수 지정
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

    /** 과목별 강의 리뷰 목록 조회 로직(추천 강의 화면)
     * @param count 정렬용 변수
     * @return 과목별 최근 강의 리뷰 목록
     */
    @Override
    public List<LectureReviewDTO.Read> readRecentLectureReviews(Subject subject, int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 과목별 최근 강의 리뷰 카운트만큼 인기순 정렬
        return lectureReviewRepository.findRecentReviewsBySubject(subject, pageable)
                .stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
    /**
     * 선생님 최신 강의 수강평 조회
     *
     * @param teacherId 선생님 아이디
     * @param count 리스트 카운트용 변수(보여지는 개수)
     * @return 해당 선생님 최신 강의 수강평 리스트
     */
    @Override
    public List<LectureReviewDTO.Read> readRecentReview(Long teacherId, int count) {
        // [1] 정렬을 위해 필요한 변수 불러오기
        Pageable pageable = PageRequest.of(0, count);
        // [2] 해당하는 선생님 ID를 통해 최근 리뷰 조회 후 리스팅
        return lectureReviewRepository.findRecentReviewsByTeacherId(teacherId, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }

    /** 강의 평점 업데이트 로직
     * @param lectureId 선생님 ID
     * @return 평점 계산 결과
     */
    @Override
    public Double updateAverageRatings(Long lectureId) {
        final double avgRating = Math.round(
                (lectureReviewRepository.findByLecture_LectureId(lectureId)
                        .stream()
                        .map(DTOMapper::toReadDTO)
                        .mapToInt(LectureReviewDTO.Read::getRating)
                        .average()
                        .orElse(0.0)) * 100.0
        ) / 100.0;

        lectureRepository.findById(lectureId).ifPresent(lecture -> {
            lecture.updateAverageRate(avgRating);

            Teacher teacher = lecture.getTeacher();
            if (teacher != null) {
                long totalReviews = lectureReviewRepository.countByLecture_Teacher_TeacherId(teacher.getTeacherId());
                Double teacherAvg = lectureReviewRepository.calculateTeacherAverageRating(teacher.getTeacherId());
                teacherAvg = teacherAvg != null ? teacherAvg : 0.0;

                teacher.updateAverageRating(Math.round(teacherAvg * 100.0) / 100.0);
                teacherRepository.save(teacher);
            }

            lectureRepository.save(lecture);
        });

        return avgRating;
    }

    @Override
    public void createReview(LectureReviewDTO.Write dto, Long memberId) {
        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        LectureReview reviewEntity = DTOMapper.toEntity(dto, lecture, member);
        lectureReviewRepository.save(reviewEntity);

        updateAverageRatings(dto.getLectureId());

    }
}
