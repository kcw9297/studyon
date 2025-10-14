package studyon.app.layer.domain.member.service;


import studyon.app.layer.base.dto.Page;
import studyon.app.common.enums.Provider;
import studyon.app.layer.domain.member.MemberDTO;

public interface MemberService {

    Page.Response<MemberDTO.Read> readPagedList(MemberDTO.Search rq, Page.Request prq);

    MemberDTO.Read join(MemberDTO.Join rq);

    MemberDTO.Read login(String email, String password);

    MemberDTO.Read socialLogin(String providerId, Provider provider);

    void editPassword(Long memberId, String password);

    void editNickname(Long memberId, String nickname);

    void withdraw(Long memberId);

    void recover(Long memberId);
}
