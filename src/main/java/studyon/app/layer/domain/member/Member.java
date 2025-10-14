package studyon.app.layer.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Role;
import studyon.app.common.enums.SocialProvider;

import java.time.LocalDateTime;

/**
 * 멤버 엔티티 클래스
 * @version 1.0
 * @author kcw97
 */


@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@Table(
        uniqueConstraints = { // 복합 UK
                @UniqueConstraint(columnNames = {"provider_id", "social_provider"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100, unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime lastLoginAt;

    private LocalDateTime withdrawAt;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(updatable = false)
    private String providerId;  // 소셜 로그인 번호

    @Enumerated(value = EnumType.STRING)
    @Column(updatable = false)
    private SocialProvider socialProvider; // 소셜 로그인 provider (GOOGLE, NAVER, KAKAO, ...)

    @Builder
    public Member(String email, String nickname, String password, String providerId, SocialProvider socialProvider) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.providerId = providerId;
        this.socialProvider = socialProvider;
        this.isActive = false;
    }

    /*
        JPA 변경 감지를 이용한 갱신 로직 (setter)
     */

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void withdraw() {
        this.withdrawAt = LocalDateTime.now();
    }

    public void recover() {
        this.withdrawAt = null;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
