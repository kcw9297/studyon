package studyon.app.layer.controller.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.common.constant.URL;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.member.service.MemberService;



/**
 * 관리자 전용 회원 REST API 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(URL.MEMBER_ADMIN_API)
@RequiredArgsConstructor
public class AdminMemberRestController {

    private final MemberService memberService;

    /**
     * [POST] 회원 비밀번호 초기화
     * @param memberId 대상 회원번호
     */
    @PostMapping("/{memberId}/password/init")
    public ResponseEntity<?> initPassword(@PathVariable Long memberId) {

        // [1] 회원 프로필 조회
        String password = memberService.initPassword(memberId);
        
        // [2] 성공 응답 반환
        return RestUtils.ok(Rest.Message.of("비밀번호 초기화 성공!"), password);
    }
}
