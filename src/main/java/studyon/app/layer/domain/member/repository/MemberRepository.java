package studyon.app.layer.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.common.enums.SocialProvider;
import studyon.app.layer.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByProviderIdAndSocialProvider(String providerId, SocialProvider socialProvider);
}
