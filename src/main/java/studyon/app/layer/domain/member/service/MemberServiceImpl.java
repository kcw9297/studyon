package studyon.app.layer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.layer.base.dto.Page;
import studyon.app.common.enums.Provider;
import studyon.app.common.exception.domain.LoginException;
import studyon.app.common.exception.domain.NotFoundException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.MemberDTO;
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
    public MemberDTO.Read join(MemberDTO.Join rq) {

        // [1] DTO -> Entity
        Member entity = DTOMapper.toEntity(rq);

        // [2] save
        Member savedEntity = memberRepository.save(entity);

        // [3] savedMemberEntity -> DTO
        return DTOMapper.toReadDTO(savedEntity);
    }

    @Override
    public MemberDTO.Read login(String email, String password) {

        // [1] entity 조회
        Member entity = memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new LoginException("아이디와 비밀번호가 일치하지 않습니다"));

        // [2] 검증
        // [2-1] 아이디 - 패스워드 일치 판단 (암호화는 추후에 수행)
        validateLogin(password, entity);

        // [3] 로그인 시간 갱신
        entity.login();

        // [4] 회원 정보 반환 (entity -> DTO)
        return DTOMapper.toReadDTO(entity);
    }


    // 로그인 검증
    private void validateLogin(String password, Member entity) {
        if (!Objects.equals(password, entity.getPassword()))
            throw new LoginException("아이디와 비밀번호가 일치하지 않습니다");

        if (Objects.nonNull(entity.getWithdrawAt()))
            throw new LoginException("탈퇴한 회원입니다");
    }


    @Override
    public MemberDTO.Read socialLogin(String providerId, Provider provider) {
        return memberRepository
                .findByProviderIdAndSocialProvider(providerId, provider)
                .map(DTOMapper::toReadDTO)
                .orElse(null);
    }


    @Override
    public void editPassword(Long memberId, String password) {

        // [1] entity 조회
        Member entity = findEntityById(memberId);

        // [2] 암호화 후 비번 갱신 (SHA-256)
        // 아직 미적용

        // [3] 비밀번호 갱신 수행
        entity.updatePassword(password);
    }

    @Override
    public void editNickname(Long memberId, String nickname) {

        // [1] entity 조회
        Member entity = findEntityById(memberId);

        // [2] 암호화 후 비번 갱신 (SHA-256)
        // 아직 미적용

        // [3] 닉네임 갱신 수행
        entity.updateNickname(nickname);
    }

    @Override
    public void withdraw(Long memberId) {

        // [1] entity 조회
        Member entity = findEntityById(memberId);

        // [2] 탈퇴 시점 기록 (시점이 지나면 탈퇴 처리)
        entity.withdraw();
    }

    @Override
    public void recover(Long memberId) {

        // [1] entity 조회
        Member entity = findEntityById(memberId);

        // [2] 탈퇴 시점 기록 삭제 (탈퇴 처리하지 않음)
        entity.recover();
    }

    private Member findEntityById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않은 회원이거나, 이미 탈퇴한 회원입니다"));
    }
}
