package studyon.app.layer.domain.member.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.service.MemberService;


/**
 * 관리자 전용 회원 REST API 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.MEMBER_ADMIN_API)
@RequiredArgsConstructor
public class AdminMemberRestController {

    private final MemberService memberService;


    /**
     * ✅ 전체 회원 목록 조회 (검색 + 페이징)
     * URL: GET /admin/api/members/list
     */
    @GetMapping("/list")
    public ResponseEntity<?> readAllMembers(MemberDTO.Search rq, Page.Request prq, HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);

        log.info("[ADMIN] 회원 목록 조회 요청 - 관리자: {}",
                profile != null ? profile.getNickname() : "비로그인");

        Page.Response<MemberDTO.Read> members = memberService.readPagedList(rq, prq);
        return RestUtils.ok(members);
    }
}
