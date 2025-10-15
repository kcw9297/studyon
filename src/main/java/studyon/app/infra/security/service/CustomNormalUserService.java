package studyon.app.infra.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * 일반 로그인 회원정보(UserDetails)를 조회할 UserDetailsService
 * @version 1.0
 * @author kcw97
 */

@Service
@RequiredArgsConstructor
@Component
public class CustomNormalUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
