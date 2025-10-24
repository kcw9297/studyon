package studyon.app.layer.domain.teacher.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.Subject;
import studyon.app.common.enums.View;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.util.List;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khj00 최초 작성
 */

/**
 * 선생님 관련 서비스 연결 컨트롤러 클래스
 * @version 1.0
 * @author khj00
 */

@RequestMapping(Url.TEACHER)
@Slf4j
@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final CacheManager cacheManager;

    /**
     * [GET] 강의 생성 뷰
     */
    @GetMapping("/lecture/register")
    public String registerLectureView(Model model) {
        return ViewUtils.returnView(model, View.TEACHER,"management/lecture_register");
    }

    /**
     * [GET] 과목별 선생님 목록 뷰
     * @param subject 교과목
     * @return JSP 뷰
     */
    @GetMapping("/find/{subject}")
    public String teacherListView(@PathVariable Subject subject, Model model) {
        // [1] 과목별 데이터 조회
        List<TeacherDTO.Read> teachers = teacherService.readTeachersBySubject(subject);
        // [2] JSP에 전달할 데이터 추가
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);

        log.info("Loaded {} teachers for subject {}" , teachers.size(), subject);

        // [3] JSP 경로 반환
        return ViewUtils.returnView(model, View.LECTURE, "teacher_list");
    }

    /**
     * [GET] 학생들에게 보여지는 선생님 프로필 페이지
     * @param teacherId 선생님 ID
     * @param count 보여지는 개수 조정을 위한 카운트 변수
     * @return JSP 뷰
     */
    @GetMapping("/profile/{teacherId}")
    public String showProfile(@PathVariable Long teacherId, Model model, @RequestParam(defaultValue = "5") int count) {
        // [1] 프로필 불러오기
        TeacherDTO.Read profile = teacherService.read(teacherId);
//        List<LectureDTO.Read> bestLectures = teacherService.readBestLectures(teacherId, count);
        //List<LectureDTO.Read> recentLectures = teacherService.readRecentLectures(teacherId, count);
        //List<LectureReviewDTO.Read> comment = teacherService.readRecentReview(teacherId, count);
        // [2] 모델 속성 설정
        model.addAttribute("teacherId", teacherId);
//        model.addAttribute("bestLectures", bestLectures);
        //model.addAttribute("recentLectures", recentLectures);
        //model.addAttribute("comment", comment);
        model.addAttribute("teacherProfile", profile);

        return ViewUtils.returnView(model, View.TEACHER, "teacher_profile");
    }

    @GetMapping("/management/profile")
    public String teacherManagementProfile(Model model, HttpServletRequest request) {

        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 로그인되지 않은 접근 → redirect to /login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        log.info(profile.toString());
        return ViewUtils.returnView(model, View.TEACHER, "management_profile");
    }

    @GetMapping("/management/lectureregister")
    public String lectureregister(Model model, HttpServletRequest request) {

        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 로그인되지 않은 접근 → redirect to /login");
            return "redirect:/login";
        }
        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        log.info(profile.toString());
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_register");
    }

    @GetMapping("/management/lecturelist")
    public String lectureList(Model model, HttpServletRequest request) {

        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 로그인되지 않은 접근 → redirect to /login");
            return "redirect:/login";
        }

        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        log.info("🎓 [강의관리 페이지 진입] memberId={}, nickname={}", memberId, profile.getNickname());
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture");
    }

    @GetMapping("/management/lectureinfo")
    public String lectureinfo(Model model, HttpServletRequest request) {

        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 로그인되지 않은 접근 → redirect to /login");
            return "redirect:/login";
        }

        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        model.addAttribute("profile", profile);
        log.info("🎓 [강의관리 페이지 진입] memberId={}, nickname={}", memberId, profile.getNickname());
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_info");
    }
}
