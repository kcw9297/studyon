package studyon.app.domain.member.service;


import studyon.app.common.dto.Page;
import studyon.app.common.enums.SocialProvider;
import studyon.app.domain.member.MemberDTO;

public interface MemberService {

    Page.Response<MemberDTO.Read> readPagedList(MemberDTO.Search rq, Page.Request prq);

    MemberDTO.Read join(MemberDTO.Join rq);

    MemberDTO.Read login(String email, String password);

    MemberDTO.Read socialLogin(String providerId, SocialProvider socialProvider);

    void editPassword(Long memberId, String password);

    void editNickname(Long memberId, String nickname);

    void withdraw(Long memberId);

    void recover(Long memberId);
}
