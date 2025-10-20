package studyon.app.layer.controller.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Subject;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.repository.TeacherService;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 JSON 데이터 불러오는 RestController
 * @version 1.0
 * @author khj00
 */

@RestController
@RequestMapping(URL.API + URL.TEACHERS)
@RequiredArgsConstructor
public class TeacherRestController {

    // [0] 선생님 서비스 불러오기
    private final TeacherService teacherService;

    // [1-1] 모든 선생님 정보 가져오기
    @GetMapping
    public List<TeacherDTO.Read> getAllTeachers() {
        return teacherService.findAllTeachers();
    }

    // [1-2] 과목별로 선생님 정보 가져오기
    @GetMapping(URL.SUBJECT + "/{subject}")
    public List<TeacherDTO.Read> getTeachersBySubject(@PathVariable Subject subject) {
        return teacherService.findTeachersBySubject(subject);
    }
}
