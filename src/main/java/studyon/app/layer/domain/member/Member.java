package studyon.app.layer.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Role;
import studyon.app.common.enums.Provider;

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
        uniqueConstraints = { // 복합 UK (소셜회원번호, 소셜유형) 정보는 고유 값
                @UniqueConstraint(columnNames = {"provider_id", "provider"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 20, unique = true)
    private String loginId; // 일반회원 로그인 아이디

    @Column(nullable = false)
    private String password; // 일반회원 로그인 비밀번호

    @Column(length = 100)
    private String email; // 일반회원 비밀번호 찾기용 이메일

    @Column(length = 100, unique = true, nullable = false)
    private String nickname; // 고유 닉네임

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime lastLoginAt; // 마지막 로그인 날짜

    private LocalDateTime withdrawAt; // 탈퇴 시점

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isActive; // 활성 계정 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 회원 역할 (Spring Security Role 대응)

    @Column(updatable = false)
    private String providerId;  // 소셜 로그인 번호

    @Enumerated(value = EnumType.STRING)
    @Column(updatable = false)
    private Provider provider; // 소셜 로그인 provider (GOOGLE, NAVER, KAKAO, ...)

    @Builder
    public Member(String loginId, String password, String email, String nickname, LocalDateTime lastLoginAt, LocalDateTime withdrawAt, String providerId, Provider provider) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.lastLoginAt = lastLoginAt;
        this.withdrawAt = withdrawAt;
        this.providerId = providerId;
        this.provider = provider;
        setDefault();
    }

    /* default 값 세팅 */

    private void setDefault() {
        this.isActive = true;
        this.role = Role.ROLE_STUDENT;
    }


    /* JPA 변경 감지를 이용한 갱신 로직 (setter) */

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
