package studyon.app.layer.base.runner;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.Env;
import studyon.app.common.utils.EnvUtils;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 애플리케이션 시작 후 로컬 환경에서 테스트 데이터 삽입 처리 클래스
 * @version 1.0
 * @author kcw97
 */

@Profile(Env.PROFILE_LOCAL)
@Component
@Transactional
@RequiredArgsConstructor
public class LocalInsertMemberDataRunner implements ApplicationRunner {

    private final Environment env;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // [1] 삽입 전 현재 환경설정 확인
        boolean isLocal = EnvUtils.hasProfile(env, Env.PROFILE_LOCAL);
        boolean isCreate = EnvUtils.isPropertyEquals(env, Env.PROP_DDL_AUTO, Env.DDL_AUTO_CREATE);

        // [2] 로컬 환경이고, ddl-auto 옵션이 create 인 경우에만 생성
        if (/*isLocal &&*/ isCreate) {

            List<Member> members = IntStream.rangeClosed(1, 5)
                    .mapToObj(i ->
                            Member.joinNormalStudent(
                                    "%s@a.a".formatted("abc%03d".formatted(i)),
                                    passwordEncoder.encode("asd"),
                                    "abc%s".formatted(StrUtils.createRandomNumString(3))
                            )
                    )
                    .collect(Collectors.toList());

            members.add(Member.createAdmin("admin@a.a", passwordEncoder.encode("admin"),"관리자"));
            memberRepository.saveAll(members);
        }
    }


}
