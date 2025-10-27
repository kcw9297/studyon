package studyon.app.layer.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import studyon.app.common.enums.Provider;
import studyon.app.common.enums.Role;
import studyon.app.infra.aop.LogInfo;
import studyon.app.layer.base.validation.annotation.Email;
import studyon.app.layer.base.validation.annotation.Password;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 인증 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AuthDTO {

    @Data
    @Builder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Join extends LogInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @Email
        private String email;

        @Password
        private String password;
    }

}
