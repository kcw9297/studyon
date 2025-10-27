package studyon.app.layer.domain.auth.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Param;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.validation.annotation.Password;
import studyon.app.layer.domain.auth.AuthDTO;
import studyon.app.layer.domain.auth.service.AuthService;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.service.MemberService;

/**
 * 인증 요청을 받는 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.AUTH_API)
@RequiredArgsConstructor
@Validated
public class AuthRestController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/join/request")
    public ResponseEntity<?> requestJoin(@Validated AuthDTO.Join rq, HttpSession session) {

        // [1] 이메일 인증 요청 발송
        authService.sendJoinEmail(rq.getEmail(), rq);

        // [2] 발송 성공 응답 반환
        session.setAttribute(Param.VERIFIED, true); // 인증 세션 생성
        return RestUtils.ok(AppStatus.OK, Url.AUTH_JOIN_MAIL);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@NotBlank String token, HttpSession session) {

        // [1] 이메일 인증 요청 검증 후, 회원가입 정보 추출
        AuthDTO.Join rq = authService.verifyAndGetData(token, AuthDTO.Join.class);
        log.warn(rq.toString());

        // [2] 회원가입 수행
        MemberDTO.Read read = memberService.join(DTOMapper.toJoinDTO(rq));

        // [3] 가입 성공 응답 반환
        authService.removeAuthRequest(token); // 저장 성공 시, 인증 요청 삭제
        return RestUtils.ok(read);
    }


    @PostMapping("/edit-password/request")
    public ResponseEntity<?> requestEditPassword(String email) {

        // [1] 이메일 인증 요청 발송
        authService.sendPasswordEditEmail(email);

        // [2] 발송 성공 응답 반환
        return RestUtils.ok();
    }


    @PatchMapping("/edit-password")
    public ResponseEntity<?> verifyAndInitPassword(
            @NotBlank String token, @Password String password) {

        // [1] 인증요청 검증 (token 기반)
        // 만약 인증에 실패한 경우, 예외가 발생하여 로직 중단
        String email = authService.verifyAndGetData(token, String.class);

        // [2] 요청 성공 시, 새로운 비밀번호로 설정
        memberService.editPassword(email, password);
        authService.removeAuthRequest(token); // 저장 성공 시, 인증 요청 삭제

        // [3] 비밀번호 변경 성공 응답 반환
        return RestUtils.ok(AppStatus.AUTH_OK_EDIT_PASSWORD);
    }














}