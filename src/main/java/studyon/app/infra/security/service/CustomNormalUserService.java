package studyon.app.infra.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import studyon.app.common.constant.MsgConst;
import studyon.app.common.enums.Provider;
import studyon.app.infra.security.dto.CustomUserDetails;
import studyon.app.infra.security.exception.BeforeWithdrawalException;
import studyon.app.infra.security.exception.WithdrawalException;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * 일반 로그인 회원정보(UserDetails)를 조회할 UserDetailsService
 * @version 1.0
 * @author kcw97
 */

@Service
@RequiredArgsConstructor
@Component
public class CustomNormalUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 만료 시간 (분)
    private static final int EXPIRATION = 3;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // [1] 일반 로그인 회원 조회
        Member member = memberRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(MsgConst.MESSAGE_INCORRECT_EMAIL_PASSWORD));

        // [2] 검증
        checkWithdrawal(member); // 탈퇴를 마친 회원인가?
        check


        return CustomUserDetails.createNormal(
                member.getMemberId(), member.getEmail(), member.getPassword(), member.getNickname(), member.getIsActive(),
                List.of(new SimpleGrantedAuthority(member.getRole().name()))
        );
    }


    private static void checkWithdrawal(Member member) {
        boolean isInactive = !member.getIsActive();
        boolean isWithdrawal = Objects.nonNull(member.getWithdrawAt());
        boolean isWithdrawalOver = isWithdrawal && member.getWithdrawAt().plusDays(EXPIRATION).isAfter(LocalDateTime.now());


        if (isInactive) {
            if (isWithdrawalOver)
                throw new BeforeWithdrawalException("탈퇴전 회원"); // 테스트 메세지 (실제로는 탈퇴복구 로직으로 해야함)
            else
                throw new WithdrawalException(MsgConst.MESSAGE_WITHDRAWAL);
        }
    }
}
