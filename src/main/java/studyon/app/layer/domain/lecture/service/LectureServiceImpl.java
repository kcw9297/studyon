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
import studyon.app.layer.domain.lecture_review.repository.LectureReviewRepository;

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

    /** 최근 강의 리스트 불러오는 메소드
     * @param subject 과목
     * @param count 카운트용 변수
     * @return 최근 강의 리스트
     */
    @Override
    public List<LectureDTO.Read> readRecentLectures(Subject subject, int count) {
        // [1] 리스팅 카운트용 변수
        Pageable pageable = PageRequest.of(0, count);
        // [2] 과목 기반으로 최근 강의 정렬
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
        // [2] 과목 기반으로 BEST 강의 정렬
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

    @Override
    public Long readAllLectureCount() {
        // [1] 총 강의 수 리턴
        return lectureRepository.count();
    }


}
