package studyon.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import studyon.app.layer.base.dto.Rest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Msg {

    // CONTENT
    public static final String INCORRECT_EMAIL_PASSWORD = "아이디와 비밀번호가 일치하지 않습니다";
    public static final String WITHDRAWAL = "이미 탈퇴한 회원입니다";
    public static final String DISABLED = "현재 정지된 상태입니다.\n관리자에게 문의하세요";
    public static final String SERVER_ERROR = "오류가 발생했습니다.\n잠시 후에 시도해 주세요";
    public static final String NOT_FOUND_MEMBER = "존재하지 않은 회원이거나, 이미 탈퇴한 회원입니다";
    public static final String NOT_FOUND_TEACHER = "존재하지 않는 선생님입니다. \n 선생님 ID를 확인해주세요";

    // validator message
    public static final String VALIDATOR_LONG_RANGE = "%d-%d 사이 값 입력";
    public static final String VALIDATOR_DOUBLE_RANGE = "%.2f-%.2f 사이 값 입력";


    // Rest Message
    public static final Rest.Message REST_OAUTH2_AUTHENTICATION_ERROR =
            Rest.Message.of("로그인 실패", "소셜로그인 정보를 얻어오지 못했습니다.\n잠시 후에 다시 사도해 주세요");

    public static final Rest.Message REST_SERVER_ERROR =
            Rest.Message.of("오류", "서버 오류가 발생했습니다.\\n잠시 후에 다시 사도해 주세요");

}
