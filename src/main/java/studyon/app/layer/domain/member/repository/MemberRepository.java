package studyon.app.layer.domain.member.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.common.enums.Provider;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("""
        SELECT m
        FROM Member m
        LEFT JOIN FETCH m.profileImage
        WHERE m.memberId = :memberId
    """)
    Optional<Member> findByIdWithProfileImage(Long memberId);

    Optional<Member> findByEmailAndProvider(String email, Provider provider);

    Optional<Member> findByProviderIdAndProvider(String providerId, Provider provider);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByMemberIdAndIsActive(Long memberId, Boolean isActive);

    boolean existsByMemberIdAndIsActive(Long memberId, Boolean isActive);


    /** ✅ 오늘 가입한 회원 수 조회 */
    @Query("""
        SELECT COUNT(m)
        FROM Member m
        WHERE m.cdate BETWEEN :start AND :end
    """)
    Long countMembersJoinedBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    /** 최근(7일 로그인까지) 활성 회원 수 조회 */
    @Query("""
        SELECT COUNT(m)
        FROM Member m
        WHERE m.lastLoginAt BETWEEN :start AND :end
    """)
    Long countByLastLoginAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    boolean existsByEmailAndProvider(String email, Provider provider);

    boolean existsByEmailAndProviderAndIsActive(String email, Provider provider, Boolean isActive);
}
