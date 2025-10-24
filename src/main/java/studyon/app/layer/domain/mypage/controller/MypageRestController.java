package studyon.app.layer.domain.mypage.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberProfile;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-20) : khs97 최초 작성
 *  ▶ ver 1.1 (2025-10-24) : kcw97 전체적 로직 변경
 */


/**
 * 마이파이지 비즈니스 로직 처리
 * @version 1.1
 * @author khs97
 */

@Slf4j
@RestController
@RequestMapping(Url.MYPAGE_API)
@RequiredArgsConstructor
public class MypageRestController {


}