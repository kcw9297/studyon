package studyon.app.layer.controller.member;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.View;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.service.MemberService;


/**
 * 회원 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Controller
@RequestMapping(URL.MEMBER)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * [GET] 프로필 조회
     */
    @GetMapping
    public String readProfile(HttpServletRequest request, Model model) {

        // [1] 회원 프로필 조회
        Long memberId = SessionUtils.getMemberId(request);
        MemberProfile profile = memberService.readProfile(memberId);

        // [2] 조회 데이터 삽입
        model.addAttribute("read", profile);

        // [3] 성공 응답 반환
        return ViewUtils.returnView(model, View.MEMBER, "mypage/profile");
    }
}
