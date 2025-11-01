package studyon.app.infra.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.enums.Provider;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 *  ▶ ver 1.1 (2025-10-16) : kcw97 UserServiceUtils 기능 사용으로 코드 축소
 */

/**
 * 일반 로그인 회원정보(UserDetails)를 조회할 UserDetailsService
 * @version 1.1
 * @author kcw97
 */

@Transactional(readOnly = true) // 사용하지 않으면, 조회 작업 시 LAZY 로딩에 문제 발생
@RequiredArgsConstructor
public class CustomNormalUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // [1] 일반 로그인 회원 조회
        // 이메일로 조회되는 회원이 없으면 예외 반환
        Member member = memberRepository
                .findByEmailAndProvider(email, Provider.NORMAL)
                .map(UserServiceUtils::checkWithdrawal) // 탈퇴회원 검증
                .orElseThrow(() -> new UsernameNotFoundException(""));

        // [2] 일반회원 UserDetails 객체 생성 및 반환
        return UserServiceUtils.createUserDetails(member);
    }

}
