package studyon.app.layer.domain.teacher.repository;

import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.List;

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
    // 전체 선생님 조회
    List<TeacherDTO.Read> findAllTeachers();
    // 과목별 선생님 조회
    List<TeacherDTO.Read> findTeachersBySubject(Subject subject);
    // 선생님 프로필 가져오기
    TeacherDTO.Read getTeacherProfile(Long teacherId);
    // 선생님 프로필 업데이트(필요시)
    void updateTeacherProfile(Long teacherId, TeacherDTO.Edit dto);
}
