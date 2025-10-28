package studyon.app.layer.domain.member.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
     * 전체 회원 목록 조회 (검색 + 페이징)
     * URL: GET /admin/api/members/list
     */
    @GetMapping("/list")
    public ResponseEntity<?> readAllMembers(MemberDTO.Search rq, Page.Request prq, HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        log.info("🟦 [CONTROLLER] filter={}, keyword={}, page={}, size={}, startPage={}",
                rq.getFilter(), rq.getKeyword(), prq.getPage(), prq.getSize(), prq.getStartPage());
        log.info("[ADMIN] 회원 목록 조회 요청 - 관리자: {}",
                profile != null ? profile.getNickname() : "비로그인");

        Page.Response<MemberDTO.Read> members;

        Boolean hasSearch =
                (rq.getKeyword() != null && !rq.getKeyword().isBlank())
                        || (rq.getRole() != null && !rq.getRole().isBlank())
                        || (rq.getIsActive() != null && !"".equals(String.valueOf(rq.getIsActive())));


        if (hasSearch) {
            members = memberService.search(rq, prq); // 검색 모드
        } else {
            members = memberService.readPagedList(rq, prq); // 전체 목록 모드
        }
        return RestUtils.ok(members);
    }

    /**
     * [GET] 회원 목록을 PDF로 다운로드
     * URL: GET /admin/members/export/pdf
     * @param rq 멤버 목록 요청 리퀘스트
     */

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportMemberListPdf(MemberDTO.Search rq) {
        log.info("📤 [CONTROLLER] 회원 목록 PDF 다운로드 요청");

        byte[] pdfBytes = memberService.generateMemberListPdf(rq);

        // [1] http 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "member_list.pdf");
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        // [2] 응답 반환
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/toggle/{memberId}")
    public ResponseEntity<?> toggleActive(@PathVariable Long memberId) {
        memberService.toggleActive(memberId);
        return RestUtils.ok();
    }
}
