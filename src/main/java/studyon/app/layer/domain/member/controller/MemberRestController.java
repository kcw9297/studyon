package studyon.app.layer.domain.member.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.URL;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.service.MemberService;


/**
 * 회원 REST API 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(URL.MEMBER_API)
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    /**
     * [POST] 회원 비밀번호 초기화
     */
    @PostMapping("/password/init")
    public ResponseEntity<?> initPassword(HttpServletRequest request) {

        // [1] 회원 프로필 조회
        Long memberId = SessionUtils.getMemberId(request);
        String password = memberService.initPassword(memberId);

        // [2] 성공 응답 반환
        return RestUtils.ok(Rest.Message.of("비밀번호 초기화 성공!"), password);
    }


}
