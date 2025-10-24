package studyon.app.layer.domain.member.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.Url;
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
@RequestMapping(Url.MEMBER)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


}
