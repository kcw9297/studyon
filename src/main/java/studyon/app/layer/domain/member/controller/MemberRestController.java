package studyon.app.layer.domain.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.validation.annotation.Nickname;
import studyon.app.layer.base.validation.annotation.Password;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.service.MemberService;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 프로필 관련 로직 추가
 */

/**
 * 회원 REST API 클래스
 * @version 1.1
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.MEMBER_API)
@RequiredArgsConstructor
@Validated // 파라미터에도 BeanValidation 적용
public class MemberRestController {

    private final MemberService memberService;

    /**
     * [GET] 회원 프로필 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<?> readProfile(HttpSession session) {

        // [1] 회원 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 데이터 반환
        return RestUtils.ok(profile);
    }

    /**
     * [PATCH] 회원 프로필 이미지 변경
     */
    @PatchMapping("/profile_image")
    public ResponseEntity<?> editProfileImage(HttpSession session, MultipartFile profileImage) {

        log.warn("editProfileImage 수행");

        // [1] 회원번호 조회
        Long memberId = SessionUtils.getMemberId(session);

        // [2] 회원 프로필 이미지 갱신
        memberService.editProfileImage(memberId, profileImage);

        // [3] 성공 상태만 반환
        return RestUtils.ok();
    }


    /**
     * [POST] 회원 비밀번호 초기화
     */
    @PatchMapping("/password")
    public ResponseEntity<?> editPassword(HttpSession session, @Password String password) {

        // [1] 회원 프로필 조회
        MemberProfile profile = SessionUtils.getProfile(session);

        // [2] 비밀번호 변경
        memberService.editPassword(profile.getEmail(), password);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.MEMBER_OK_EDIT_PASSWORD);
    }


    /**
     * [POST] 회원 비밀번호 초기화
     */
    @PatchMapping("/nickname")
    public ResponseEntity<?> editNickname(@Nickname String nickname, HttpSession session) {

        // [1] 회원번호 조회
        Long memberId = SessionUtils.getMemberId(session);

        // [2] 닉네임 변경
        memberService.editNickname(memberId, nickname);

        // [3] 성공 응답 반환
        return RestUtils.ok(AppStatus.MEMBER_OK_EDIT_NICKNAME);
    }
}
