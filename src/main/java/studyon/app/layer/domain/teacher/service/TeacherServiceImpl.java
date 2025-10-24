package studyon.app.layer.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.Msg;
import studyon.app.common.constant.StatusCode;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.exception.BusinessException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;


import java.util.stream.Collectors;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 서비스 구현체
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;
    private final LectureReviewRepository lectureReviewRepository;

    /**
     * 모든 선생님 조회
     * @return 선생님 리스트
     */

    @Override
    public List<TeacherDTO.Read> readAllTeachers() {
//        // [0] 리스팅 카운트용 변수
//        Pageable pageable = PageRequest.of(0, count);
        // [1] 레포지토리에서 모든 선생님 정보 가져오기
        return teacherRepository.findAll().stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }

    /**
     * 과목별 선생님 조회
     * @return 해당 선생님 리스트
     */
    @Override
    public List<TeacherDTO.Read> readTeachersBySubject(Subject subject) {
//        // [1] 리스팅 카운트용 변수
//        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.ASC, "teacherId"));
        // [2] 레포지토리에서 과목별로 선생님 정보 가져와서 DTO 변환 후 리스팅, 'teacherId' 필드를 기준으로 오름차순 정렬
        return teacherRepository.findBySubject(subject).stream()
                .map(DTOMapper::toReadDTO)
                .collect(Collectors.toList());
    }
    /**
     * 선생님 프로필 가져오기
     * @param teacherId 선생님 아이디
     * @return 해당 선생님 리스트
     */
    @Override
    public TeacherDTO.Read read(Long teacherId) {
        // [1] 해당되는 ID에 따른 선생님 프로필 불러오기
        return teacherRepository.findById(teacherId)
                .map(DTOMapper::toReadDTO)
                .orElseThrow(() -> new BusinessException(Msg.NOT_FOUND_TEACHER, StatusCode.TEACHER_NOT_FOUND));
    }

    /**
     * 선생님 프로필 업데이트(필요시)
     * @param teacherId 선생님 아이디
     * @param dto 선생님 정보를 담은 DTO - 업데이트용
     */
    @Override
    public void update(Long teacherId, TeacherDTO.Edit dto) {
        // [1] 선생님 아이디로 조회 후 정보 수정 및 저장
        teacherRepository.findById(teacherId)
                .ifPresentOrElse(
                        teacher -> teacher.updateInfo(dto.getSubject(), dto.getDescription()),
                        () -> { throw new BusinessException(Msg.NOT_FOUND_TEACHER, StatusCode.TEACHER_NOT_FOUND); }
                );
    }
    /**
     * 선생님 담당 BEST 강의 조회(우선 수강생 수로 정렬함)
     * @param teacherId 선생님 아이디
     * @param count 리스트 카운트용 변수(보여지는 개수)
     * @return 해당 선생님 강의 리스트
     */
    @Override
    public List<LectureDTO.Read> readBestLectures(Long teacherId, int count) {
        // [1] 정렬을 위해 필요한 변수 불러오기
        Pageable pageable = PageRequest.of(0, count);
        // [2] 해당하는 선생님 ID를 통해 Best 강의 조회 후 리스팅
        return lectureRepository.findBestLecturesByTeacherId(teacherId, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
                .collect(Collectors.toList());
    }
    /**
     * 선생님 최신 강의 조회
     *
     * @param teacherId 선생님 아이디
     * @param count 리스트 카운트용 변수(보여지는 개수)
     * @return 해당 선생님 최신 강의 리스트
     */
    @Override
    public List<LectureDTO.Read> readRecentLectures(Long teacherId, int count) {
        // [1] 정렬을 위해 필요한 변수 불러오기
        Pageable pageable = PageRequest.of(0, count);
        // [2] 해당하는 선생님 ID를 통해 최근 강의 조회 후 리스팅
        return lectureRepository.findRecentLecturesByTeacherId(teacherId, pageable)
                .stream()
                .map(DTOMapper::toReadDTO) // 엔티티 → DTO
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
}
