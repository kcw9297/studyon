package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * View 유형
 * @version 1.0
 * @author kcw97
 */
@Getter
public enum View {

    TEST("test"),
    MEMBER("member"),
    TEACHER("teacher"),
    LECTURE("lecture"),
    HOME("home"),
    EDITOR("editor"),
    MYPAGE("mypage"),
    CHATBOT("chatbot"),
    USERSUPPORT("usersupport"),
    ADMIN("admin"),
    PLAYER("lecture_video"),
    RESISTER("resister"),
    PAYMENT("payment"),
    ACCOUNT("account");


    private final String value;

    View(String value) {this.value = value;}

    public static List<View> get() {
        return Arrays.asList(View.values());
    }
}
