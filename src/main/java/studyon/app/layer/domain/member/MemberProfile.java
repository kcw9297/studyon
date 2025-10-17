package studyon.app.layer.domain.member;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import studyon.app.common.enums.Provider;
import studyon.app.common.enums.Role;

import java.time.LocalDateTime;

/**
 * 로그인 회원의 프로필 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class MemberProfile {

    private Long memberId;
    private String nickname;
    private String email;
    private Provider provider;
    private Role role;
}
