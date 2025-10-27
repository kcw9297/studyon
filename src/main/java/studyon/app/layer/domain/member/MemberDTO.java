package studyon.app.layer.domain.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import studyon.app.common.enums.Role;
import studyon.app.infra.aop.LogInfo;
import studyon.app.common.enums.Provider;
import studyon.app.layer.base.validation.annotation.Email;

import java.time.LocalDateTime;

/**
 * 멤버 정보를 담은 DTO
 * @version 1.0
 * @author kcw97
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Search {

        private String filter;
        private String keyword;

        private String role;       // USER / TEACHER / ADMIN
        private Boolean isActive;  // true / false
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {

        @JsonFormat(pattern = "#,###")
        private Long memberId;

        private String email;

        private String nickname;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime lastLoginAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime cdate;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime withdrawAt;

        private Boolean isActive;

        private Provider provider;

        private Role role;
    }


    @Data
    @Builder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Join extends LogInfo {

        private String email;
        private String nickname;
        private String password;
        private String providerId;
        private Provider provider;
    }

}
