package studyon.app.layer.domain.member.service;


import org.springframework.web.multipart.MultipartFile;
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
     * 회원 목록 검색용
     * @param prq 검색 페이징 요청
     * @param rq 멤버 검색 dto
     * @return 검색 필터된 회원 정보 목록
     */
    Page.Response<MemberDTO.Read> search(MemberDTO.Search rq, Page.Request prq);

    /**
     * 회원 프로필정보 조회
     * @param memberId 대상 회원번호
     * @return 회원 프로필 정보
     */
    MemberProfile readProfile(Long memberId);

    /**
     * 회원 가입
     * @param rq 회원 가입 요청
     * @return 가입에 성공한 회원 정보
     */
    MemberDTO.Read join(MemberDTO.Join rq);

    /**
     * 비밀번호 초기화
     * @param email 대상 이메일
     * @param newPassword 새로 초기화하는 패스워드
     */
    void editPassword(String email, String newPassword);

    /**
     * 프로필 이미지 변경
     * @param memberId 대상 회원번호
     * @param profileImageFile 변경 이미지
     */
    void editProfileImage(Long memberId, MultipartFile profileImageFile);

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

    /**
     * 회원 목록 PDF 생성
     * @return PDF 바이트 배열
     */
    byte[] generateMemberListPdf(MemberDTO.Search rq);

    /**
     * 회원 활성/비활성 컨트롤
     */
    void toggleActive(Long memberId);
}
