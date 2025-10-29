package studyon.app.layer.domain.auth;


import lombok.*;
import studyon.app.layer.base.validation.annotation.Email;
import studyon.app.layer.base.validation.annotation.Password;

import java.io.Serial;
import java.io.Serializable;

/**
 * 인증 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor
public class AuthDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Join implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @Email
        private String email;

        @Password
        private String password;
    }

}
