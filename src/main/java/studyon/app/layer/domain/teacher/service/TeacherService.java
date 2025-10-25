package studyon.app.layer.domain.teacher.service;

import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;
import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface TeacherService {
    /**
     * 모든 선생님 조회
     * @return 선생님 리스트
     */
    List<TeacherDTO.Read> readAllTeachers();
    /**
     * 과목별 선생님 조회
     * @return 해당 선생님 리스트
     */
    List<TeacherDTO.Read> readTeachersBySubject(Subject subject);
    /**
     * 선생님 프로필 가져오기
     * @return 해당 선생님 리스트
     */
    TeacherDTO.Read read(Long teacherId);
    /**
     * 선생님 프로필 업데이트(필요시)
     */
    void update(Long teacherId, TeacherDTO.Edit dto);
    TeacherDTO.LectureListResponse getLectureListByTeacher(Long teacherId, Long memberId);
}
