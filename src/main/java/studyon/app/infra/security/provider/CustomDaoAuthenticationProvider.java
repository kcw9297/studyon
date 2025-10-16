package studyon.app.infra.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import studyon.app.infra.security.exception.WithdrawalException;

/**
 * AbstractUserDetailsAuthenticationProvider 커스텀 구현체
 * 대부분의 내용은 DaoAuthenticationProvider 를 그대로 쓰고, retrieveUser 부분만 예외처리 부분 수정
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RequiredArgsConstructor
public class CustomDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    private UserDetailsPasswordService userDetailsPasswordService;
    private CompromisedPasswordChecker compromisedPasswordChecker;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException("Bad credentials");
        }

        String presentedPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    protected void doAfterPropertiesSet() {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication,
                                                         UserDetails user) {
        String presentedPassword = authentication.getCredentials().toString();

        if (this.compromisedPasswordChecker != null &&
                this.compromisedPasswordChecker.check(presentedPassword).isCompromised()) {
            throw new CompromisedPasswordException(
                    "The provided password is compromised, please change your password");
        }

        if (this.userDetailsPasswordService != null &&
                this.passwordEncoder.upgradeEncoding(user.getPassword())) {
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            user = this.userDetailsPasswordService.updatePassword(user, newPassword);
        }

        return super.createSuccessAuthentication(principal, authentication, user);
    }

    /* retrieveUser 부분만 수정 */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        try {
            UserDetails loadedUser = this.userDetailsService.loadUserByUsername(username);
            if (loadedUser == null)
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            return loadedUser;

        } catch (UsernameNotFoundException | WithdrawalException | InternalAuthenticationServiceException e) {
            throw e; // 커스텀 예외는 감싸지 않음
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        this.userDetailsPasswordService = userDetailsPasswordService;
    }

    public void setCompromisedPasswordChecker(CompromisedPasswordChecker compromisedPasswordChecker) {
        this.compromisedPasswordChecker = compromisedPasswordChecker;
    }
}