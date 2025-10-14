package studyon.app.layer.base.runner;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import studyon.app.common.constant.AppProfile;

/**
 * 애플리케이션 시작 후 로컬 환경에서 테스트 데이터 삽입 처리 클래스
 * @version 1.0
 * @author kcw97
 */

@Profile(AppProfile.LOCAL)
@Component
@RequiredArgsConstructor
public class LocalInsertMemberDataRunner implements ApplicationRunner {

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

    }
}
