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
import studyon.app.common.enums.Subject;
import studyon.app.common.utils.EnvUtils;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.notice.Notice;
import studyon.app.layer.domain.notice.repository.NoticeRepository;
import studyon.app.layer.domain.teacher.Teacher;
import studyon.app.layer.domain.teacher.repository.TeacherRepository;

import java.util.*;
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
    private final TeacherRepository teacherRepository;
    private final NoticeRepository noticeRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    private static final List<String> RANDOM_NAMES = List.of(
            "김민준", "이서연", "박지후", "최지민", "정하늘",
            "한예린", "윤서준", "장도윤", "임하은", "조현우",
            "서지호", "강민지", "배도현", "문유진", "오채원",
            "신은호", "홍지아", "권하윤", "남도영", "백지후"
    );

    private static final List<Subject> subjects = Subject.get();


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // [1] 삽입 전 현재 환경설정 확인
        boolean isLocal = EnvUtils.hasProfile(env, Env.PROFILE_LOCAL);
        boolean isCreate = EnvUtils.isPropertyEquals(env, Env.PROP_DDL_AUTO, Env.DDL_AUTO_CREATE);

        // [2] 로컬 환경이고, ddl-auto 옵션이 create 인 경우에만 생성
        if (/*isLocal &&*/ isCreate) {

            List<Teacher> teachers = new ArrayList<>();

            // 학생 회원 50명 생성
            List<Member> members = IntStream.rangeClosed(1, 50)
                    .mapToObj(i ->
                            Member.joinNormalStudent(
                                    "%s@a.a".formatted("abc%03d".formatted(i)),
                                    passwordEncoder.encode("asd"),
                                    "abc%s".formatted(StrUtils.createRandomNumString(8))
                            )
                    )
                    .collect(Collectors.toList());

            // 선생님 회원 10명 생성
            List<Member> teacherMembers = IntStream.rangeClosed(1, 10)
                    .mapToObj(i ->
                            {
                                Member teacherMember = Member.createTeacherAccount(
                                        "%s@a.a".formatted("teacher%03d".formatted(i)),
                                        passwordEncoder.encode("asd"),
                                        RANDOM_NAMES.get(i)
                                );

                                teachers.add(Teacher.create(subjects.get(random.nextInt(1, 5)), teacherMember));
                                return teacherMember;
                            }
                    )
                    .collect(Collectors.toList());


            // 관리자 1명 생성
            members.add(Member.createAdmin("admin@a.a", passwordEncoder.encode("admin"),"관리자"));
            members.addAll(teacherMembers);
            memberRepository.saveAll(members);
            teacherRepository.saveAll(teachers);
            
            
            // [3] create 옵션일 시, 공지사항 카드 정보 새롭게 생성
            if (isCreate) {
                // 공지사항 엔티티 생성
                List<Notice> notices = IntStream.rangeClosed(1, 6)
                        .mapToObj(Notice::new)
                        .toList();

                // 저장
                noticeRepository.saveAll(notices);
            }
            
        }
    }


}
