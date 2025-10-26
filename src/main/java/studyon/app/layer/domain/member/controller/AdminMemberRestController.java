package studyon.app.layer.domain.member.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.domain.member.MemberDTO;
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

    @GetMapping("/list")
    public ResponseEntity<?> readAllMembers(MemberDTO.Search rq, Page.Request prq) {
        log.info("[GET] 전체 회원 목록 조회");
        Page.Response<MemberDTO.Read> members = memberService.readPagedList(rq, prq);
        return RestUtils.ok(members);
    }
}
