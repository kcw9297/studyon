package studyon.app.layer.domain.teacher.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.*;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.util.List;
import java.util.Objects;


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

        // [2] 모델 속성 설정
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("teacherProfile", profile);

        return ViewUtils.returnView(model, View.TEACHER, "teacher_profile");
    }

    @GetMapping("/management/profile")
    public String teacherManagementProfile(Model model, HttpSession session) {


        return ViewUtils.returnView(model, View.TEACHER, "management_profile");
    }


    @GetMapping("/management/lectureregister")
    public String lectureRegister(Model model, HttpSession session) {

        // [1] 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 선택 데이터 삽입
        model.addAttribute("subjectDetails", SubjectDetail.getByParent(profile.getTeacherSubject().name()));
        model.addAttribute("targets", LectureTarget.get());
        model.addAttribute("difficulties", Difficulty.get());

        // [3] view 반환
        return ViewUtils.returnView(model, View.TEACHER,"management_lecture_register");
    }


    @GetMapping("/management/lecturelist")
    public String lectureList(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture");
    }

    @GetMapping("/management/lectureinfo/{lectureId}")
    public String lectureinfo(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_info");
    }

    @GetMapping("/management/qna")
    public String qna(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_qna");
    }

    @GetMapping("/management/qna/answer")
    public String qnaAnswer(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_qna_answer");
    }

    @GetMapping("/management/qna/detail")
    public String qnaDetail(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_qna_detail");
    }

    @GetMapping("/management/qna/updateQna")
    public String qnaUpdate(Model model, HttpSession session) {
        return ViewUtils.returnView(model, View.TEACHER, "management_lecture_qna_update");
    }
}
