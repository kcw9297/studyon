package studyon.app.layer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.Msg;
import studyon.app.layer.base.dto.Page;
import studyon.app.common.enums.Provider;
import studyon.app.layer.base.exception.LoginException;
import studyon.app.layer.base.exception.NotFoundException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.member.mapper.MemberMapper;

import java.util.List;
import java.util.Objects;

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
    public Page.Response<MemberDTO.Read> readPagedList(MemberDTO.Search rq, Page.Request prq) {

        // [1] 회원 페이징
        List<MemberDTO.Read> memberInfos = memberMapper.selectAll(rq, prq);

        // [2] 페이징 결과 기반 카운트
        Integer count = memberMapper.countAll(rq);

        // [3] 조회 결과 반환
        return Page.Response.create(memberInfos, prq.getPage(), prq.getSize(), count);
    }


    @Override
    @Transactional(readOnly = true)
    public MemberProfile getProfile(Long memberId) {
        return memberRepository
                .findById(memberId)
                .map(DTOMapper::toMemberProfileDTO)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND_MEMBER));
    }


    @Override
    public void editPassword(Long memberId, String password) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND_MEMBER))
                .updatePassword(passwordEncoder.encode(password)); // 비밀번호 갱신 (암호화된 비밀번호 사용)
    }


    @Override
    public void editNickname(Long memberId, String nickname) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND_MEMBER))
                .updateNickname(nickname);
    }


    @Override
    public void withdraw(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND_MEMBER))
                .withdraw();
    }


    @Override
    public void recover(Long memberId) {
        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND_MEMBER))
                .recover();
    }

}
