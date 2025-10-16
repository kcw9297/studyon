package studyon.app.layer.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.common.enums.Role;
import studyon.app.common.enums.Provider;

import java.time.LocalDateTime;

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

    @Column(length = 100)
    private String email; // 이메일 (일반회원은 로그인 용도 사용)

    private String password; // 일반회원 로그인 비밀번호

    @Column(length = 100, unique = true, nullable = false)
    private String nickname; // 고유 닉네임

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime lastLoginAt; // 마지막 로그인 날짜

    private LocalDateTime withdrawAt; // 탈퇴 시점

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isActive; // 활성 계정 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Role role; // 회원 역할 (Spring Security Role 대응)

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Provider provider; // 소셜 로그인 provider (GOOGLE, NAVER, KAKAO, ...)

    @Column(updatable = false)
    private String providerId;  // 소셜 로그인 번호


    @Builder
    private Member(String email, String password, String nickname, Role role, Provider provider, String providerId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        setDefault();
    }

    /* default 값 세팅 */

    private void setDefault() {
        this.lastLoginAt = LocalDateTime.now();
        this.isActive = true;
    }

    /* 객체 구현체 제공 정적 메소드 */

    /**
     * 일반 학생 회원가입에 필요한 회원 엔티티 생성
     * @param email 가입 이메일
     * @param password 가입 비밀번호
     * @param nickname 가입 닉네임
     * @return 일반 회원 엔티티 객체
     */
    public static Member joinNormalStudent(String email, String password, String nickname) {

        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(Role.ROLE_STUDENT)
                .provider(Provider.NORMAL)
                .build();
    }

    /**
     * 소셜 학생 회원가입에 필요한 회원 엔티티 생성
     * @param email 가입 이메일
     * @param provider 소셜 제공자 (NORMAL 제외한 값)
     * @param providerId 소셜회원번호
     * @return 소셜 회원 엔티티 객체
     */
    public static Member joinSocialStudent(String email, String nickname, Provider provider, String providerId) {

        return Member.builder()
                .email(email)
                .nickname(nickname)
                .role(Role.ROLE_STUDENT)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    /**
     * 선생님 계정 생성
     * @param email 선생님 이메일
     * @param password 선생님 비밀번호
     * @param nickname 선생님 닉네임
     * @return 선생님 회원 엔티티 객체
     */
    public static Member createTeacherAccount(String email, String password, String nickname) {
        return new Member(email, password, nickname, Role.ROLE_TEACHER, Provider.NORMAL, null);
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

    public Member loginSocial(String email) {
        this.email = email;
        this.lastLoginAt = LocalDateTime.now();
        return this;
    }
}
