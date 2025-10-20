package studyon.app.layer.domain.member.service;


import studyon.app.layer.base.dto.Page;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;

public interface MemberService {

    /**
     * 회원 정보 조회
     * @param memberId 대상 회원번호
     * @return 회원 정보
     */
    MemberDTO.Read read(Long memberId);

    /**
     * 페이징된 회원 목록 조회
     * @param rq 검색 요청
     * @param prq 검색 페이징 요청
     * @return 페이징된 회원 정보 목록
     */
    Page.Response<MemberDTO.Read> readPagedList(MemberDTO.Search rq, Page.Request prq);

    /**
     * 회원 프로필정보 조회
     * @param memberId 대상 회원번호
     * @return 회원 프로필 정보
     */
    MemberProfile readProfile(Long memberId);

    /**
     * 비밀번호 초기화
     * @param memberId 대상 회원번호
     * @return 초기화된 비밀번호
     */
    String initPassword(Long memberId);

    /**
     * 닉네임 변경
     * @param memberId 대상 회원번호
     * @param nickname 변경 닉네임
     */
    void editNickname(Long memberId, String nickname);

    /**
     * 회원탈퇴 처리
     * @param memberId 대상 회원번호
     */
    void withdraw(Long memberId);

    /**
     * 회원탈퇴 복구
     * @param memberId 대상 회원번호
     */
    void recover(Long memberId);
}
