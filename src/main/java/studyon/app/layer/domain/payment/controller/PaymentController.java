package studyon.app.layer.domain.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.enums.View;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.member.MemberProfile;

@Slf4j
@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final LectureRepository lectureRepository;
    private final CacheManager cacheManager;

    /**
     * ✅ 결제 페이지 뷰 렌더링
     * URL: /payment/{lectureId}
     */
    @GetMapping("/{lectureId}")
    public String paymentPage(@PathVariable Long lectureId, HttpSession session, Model model) {
        // 1️⃣ 강의 조회
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. id=" + lectureId));

        // 2️⃣ 로그인 확인
        MemberProfile profile = SessionUtils.getProfile(session);
        Long memberId = profile.getMemberId();

        // 3️⃣ 모델 추가
        model.addAttribute("lecture", lecture);
        model.addAttribute("user", profile);

        log.info("✅ 결제 페이지 진입 - lectureId={}, userId={}, nickname={}",
                lectureId, profile.getMemberId(), profile.getNickname());

        // 4️⃣ JSP 렌더링 (View.PAYMENT는 enum에 정의된 view path)
        return ViewUtils.returnView(model, View.PAYMENT, "payment");
    }
}
