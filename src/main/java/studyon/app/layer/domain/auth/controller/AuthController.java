package studyon.app.layer.domain.auth.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.auth.service.AuthService;

import java.util.Objects;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-25) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-26) : kcw97 AuthController / AccountController 통합
 */


/**
 * 로그인이 필요하지 않은 인증 관린 요청 처리 클레스
 * <br>다른 컨트롤러와 달리, @RequestMapping Base Url 설정하지 않음
 * @version 1.1
 * @author kcw97
 */

@Service
@Controller
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;


    @GetMapping(Url.LOGIN)
    public String showLoginView() {
        return "page/auth/login";
    }


    @GetMapping(Url.JOIN)
    public String showJoinView() {
        return "page/auth/join";
    }

    @GetMapping(Url.AUTH_JOIN_MAIL)
    public String showJoinMailView(HttpSession session) {

        // [1] 유효한 접근인지 검증 (의도하지 않은 경로로 접근 시 리다이렉트)
        if (Objects.isNull(session.getAttribute(Param.VERIFIED)))
            return ViewUtils.redirectHome();  // 메인으로 리다이렉트

        // [2] 검증 후, 세션을 제거하고 view 반환
        session.removeAttribute(Param.VERIFIED);
        return "page/auth/join_mail";
    }


    @GetMapping(Url.AUTH_JOIN_MAIL_RESULT)
    public String showJoinMailResultView(@NotBlank String token) {

        // [1] 유효한 접근인지 검증 (의도하지 않은 경로로 접근 시 리다이렉트)
        authService.verify(token);

        // [2] 검증 후 view 반환
        return "page/auth/join_mail_result";
    }


    @GetMapping(Url.AUTH_EDIT_PASSWORD)
    public String showAuthEditPasswordView(String token) {

        // [1] 유효한 요청인지 검증
        // 실패하는 경우, 예외가 반환되어 로직 수행 불가
        authService.verify(token);

        // [2] 검증 후 비밀번호 수정 페이지로 이동
        return "page/auth/edit_password";
    }

}
