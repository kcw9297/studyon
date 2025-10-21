package studyon.app.layer.domain.member;

import lombok.*;
import studyon.app.common.enums.Provider;
import studyon.app.common.enums.Role;

import java.io.Serial;
import java.io.Serializable;

/**
 * 로그인 회원의 프로필 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class MemberProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long memberId;
    private String nickname;
    private String email;
    private Provider provider;
    private Role role;
}
