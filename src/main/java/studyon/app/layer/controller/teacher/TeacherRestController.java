package studyon.app.layer.controller.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Subject;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 JSON 데이터 불러오는 컨트롤러 클래스
 * @version 1.0
 * @author khj00
 */


@RestController
@RequestMapping(URL.API + URL.TEACHERS)  // teacher+api
@RequiredArgsConstructor
public class TeacherRestController {

    // [0] 선생님 서비스 불러오기
    private final TeacherService teacherService;

    /**
     * [GET] 모든 선생님 정보 가져오기
     */

    @GetMapping
    public ResponseEntity<?> getAllTeachers() {
        List<TeacherDTO.Read> teachers = teacherService.findAllTeachers();
        return RestUtils.ok(Rest.Message.of("선생님을 불러왔습니다."), teachers);
    }

    /**
     * [GET] 과목별 선생님 정보 가져오기
     * @param subject
     */
    @GetMapping(URL.SUBJECT + "/{subject}")
    public ResponseEntity<?> getTeachersBySubject(@PathVariable Subject subject) {
        List<TeacherDTO.Read> teachersBySubject = teacherService.findTeachersBySubject(subject);
        return RestUtils.ok(Rest.Message.of("해당 과목 선생님들을 불러왔습니다."), teachersBySubject);
    }
}
