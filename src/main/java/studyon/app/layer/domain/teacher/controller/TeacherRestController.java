package studyon.app.layer.domain.teacher.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-21) : khj00 최초 작성
 */

/**
 * 선생님 JSON 데이터 불러오는 컨트롤러 클래스
 * @version 1.0
 * @author khj00
 */

@Slf4j
@RestController
@RequestMapping(URL.TEACHERS_API)
@RequiredArgsConstructor
public class TeacherRestController {

    // [0] 선생님 서비스 불러오기
    private final TeacherService teacherService;
    private final LectureService lectureService;
    private final CacheManager cacheManager;
    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;

    /**
     * [POST] 모든 선생님 정보 가져오기
     * @return 모든 선생님 정보
     */

    @GetMapping
    public ResponseEntity<?> getAllTeachers(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" 선생님 전체 정보 GET 요청: 모든 선생님 조회");
        // [1] 모든 선생님 정보 가져와서 리스팅
        List<TeacherDTO.Read> teachers = teacherService.readAllTeachers();
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(teachers);
    }

    /**
     * [POST] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @GetMapping( "/subject/{subject}")
    public ResponseEntity<?> getTeachersBySubject(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" GET 요청: 과목 [{}]의 선생님 조회", rq.getSubject());
        // [1] 과목별로 선생님 정보 가져와서 리스팅
        List<TeacherDTO.Read> teachersBySubject = teacherService.readTeachersBySubject(rq.getSubject());
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(teachersBySubject);
    }

    @GetMapping("/management/lecturelist")
    public ResponseEntity<?> getTeacherManagement(Model model, HttpServletRequest request) {
        log.info("티쳐 api");
        Long memberId = SessionUtils.getMemberId(request);
        Teacher teacher = teacherRepository.findByMemberId(memberId);
        Long teacherId = teacher.getTeacherId();
        log.info(teacherId.toString());
        List<Lecture> pendingLectures = lectureRepository.findByLectureRegisterStatus(LectureRegisterStatus.PENDING);
        List<Lecture> registeredLectures = lectureRepository.findByLectureRegisterStatus(LectureRegisterStatus.REGISTERED);
        List<Lecture> unregisteredLectures = lectureRepository.findByLectureRegisterStatus(LectureRegisterStatus.UNREGISTERED);
        log.info(registeredLectures.toString());
        log.info(unregisteredLectures.toString());
        log.info(pendingLectures.toString());
        Map<String, Object> response = new HashMap<>();
        response.put("teacherId", teacherId);
        response.put("pending", pendingLectures);
        response.put("registered", registeredLectures);
        response.put("unregistered", unregisteredLectures);

        return RestUtils.ok(response);


    }
}
