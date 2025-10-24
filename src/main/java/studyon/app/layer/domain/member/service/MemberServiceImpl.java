package studyon.app.layer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.Msg;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.dto.Page;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.member.mapper.MemberMapper;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public MemberDTO.Read read(Long memberId) {
        return memberRepository
                .findById(memberId)
                .map(DTOMapper::toReadDto)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
    }


    @Override
    @Transactional(readOnly = true)
    public Page.Response<MemberDTO.Read> readPagedList(MemberDTO.Search rq, Page.Request prq) {

        // [1] 회원 페이징
        List<MemberDTO.Read> memberReads = memberMapper.selectAll(rq, prq);

        // [2] 페이징 결과 기반 카운트
        Integer count = memberMapper.countAll(rq);

        // [3] 조회 결과 반환
        return Page.Response.create(memberReads, prq.getPage(), prq.getSize(), count);
    }


    @Override
    @Transactional(readOnly = true)
    public MemberProfile readProfile(Long memberId) {
        return memberRepository
                .findById(memberId)
                .map(DTOMapper::toMemberProfileDTO)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
    }


    @Override
    public String initPassword(Long memberId) {

        // [1] 초기화할 패스워드 문자열 생성
        String newPassword = StrUtils.createShortUUID();

        // [2] 회원 조회 후 초기화 수행
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .updatePassword(passwordEncoder.encode(newPassword)); // 암호화 후 초기화 수행

        // [3] 초기화에 성공한 비밀번호 반환
        return newPassword;
    }


    @Override
    public void editNickname(Long memberId, String nickname) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .updateNickname(nickname);
    }


    @Override
    public void withdraw(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .withdraw();
    }


    @Override
    public void recover(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND))
                .recover();
    }


    @Override
    public Long readAllMemberCount() {
        return memberRepository.count();
    }
}
