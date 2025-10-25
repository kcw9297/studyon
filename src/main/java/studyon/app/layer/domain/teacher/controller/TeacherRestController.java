package studyon.app.layer.domain.teacher.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.infra.cache.manager.CacheManager;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@RequestMapping(Url.TEACHERS_API)
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
    public ResponseEntity<?> getTeacherLectureList(HttpServletRequest request) {
        // ✅ 세션 프로필 꺼내기
        MemberProfile profile = SessionUtils.getProfile(request.getSession());

        if (profile == null || profile.getTeacherId() == null) {
            log.warn("⚠ 세션에 teacher 정보가 없습니다.");
            return RestUtils.fail(AppStatus.SESSION_EXPIRED);
        }
        Long teacherId = profile.getTeacherId();
        log.info("✅ 로그인한 강사 ID = {}", teacherId);

        TeacherDTO.LectureListResponse response = teacherService.getLectureListByTeacher(teacherId);
        return RestUtils.ok(response);
    }

    @PostMapping("lecture/register")
    public ResponseEntity<?> registerLecture(String title,String description, String category,Integer price,HttpServletRequest request) {
        MemberProfile profile = SessionUtils.getProfile(request.getSession());
        if (profile == null || profile.getTeacherId() == null) {
            log.warn("강사 정보가 없습니다.");
        }
        Long teacherId = profile.getTeacherId();

        LectureDTO.Register dto = LectureDTO.Register.builder()
                .teacherId(teacherId)
                .title(title)
                .description(description)
                .category(category)
                .price(price)
                .build();

        lectureService.registerLecture(dto);

        return RestUtils.ok("강의가 등록되었습니다.");
    }

    @GetMapping("/management/profile")
    public ResponseEntity<?> getTeacherProfile(HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherMemberId = profile.getMemberId();
        TeacherDTO.TeacherManagementProfile response = teacherService.readProfile(teacherMemberId);
        return RestUtils.ok(response);

        //가져올 정보 : 강사명, 강사 이메일, 강사 프로필, 강의 수, 수강생 수, 평균 평점
    }

    @GetMapping("/management/profile/image")
    public ResponseEntity<Resource> getTeacherProfileImage(HttpSession session) {
        // [1] 세션에서 프로필 정보 가져오기
        MemberProfile profile = SessionUtils.getProfile(session);

        if (profile == null || profile.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // [2] 실제 파일 경로 (C:PROJECT_FILE/member/xxxx.jpg)
            String filePath = "C:/PROJECT_FILE/" + profile.getProfileImage().getFilePath();
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            // [3] 파일 리소스 생성
            Resource resource = new UrlResource(path.toUri());

            // [4] Content-Type 지정
            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "image/jpeg";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
