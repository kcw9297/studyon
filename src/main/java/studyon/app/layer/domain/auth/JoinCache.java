package studyon.app.layer.domain.auth;

import lombok.*;
import studyon.app.common.enums.Provider;

import java.io.Serial;
import java.io.Serializable;

/**
 * 회원 가입시 캐시 정보를 저장할 DTO
 * @version 1.0
 * @author kcw97
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class JoinCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String nickname;
    private String password;
    private Provider passwordCheck;
}
