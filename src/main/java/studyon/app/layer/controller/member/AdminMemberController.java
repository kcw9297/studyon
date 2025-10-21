package studyon.app.layer.controller.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.View;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.service.MemberService;

import java.util.List;


/**
 * 관리자 전용 회원 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Controller
@RequestMapping(URL.MEMBER_ADMIN)
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    /**
     * [GET] 회원 목록 조회
     * @param rq 회원 검색 요청
     * @param prq 페이징 요청
     */
    @GetMapping("/list")
    public String list(Model model, MemberDTO.Search rq, Page.Request prq) {

        // [1] 회원 프로필 조회
        Page.Response<MemberDTO.Read> page = memberService.readPagedList(rq, prq);

        // [2] 조회 결과 삽입
        model.addAttribute("page", page);

        // [2] view 반환
        return ViewUtils.returnView(model, View.MEMBER, "list");
    }


    /**
     * [GET] 회원 비밀번호 초기화 API
     * @param memberId 회원번호
     */
    @GetMapping("/{memberId}")
    public String read(Model model, @PathVariable Long memberId) {

        // [1] 회원 프로필 조회
        MemberDTO.Read memberRead = memberService.read(memberId);

        // [2] 조회 결과 삽입
        model.addAttribute("read", memberRead);

        // [3] view 반환
        return ViewUtils.returnView(model, View.MEMBER, "read");
    }
}
