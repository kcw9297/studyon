package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */


/**
 * ChatRole 유형
 * @version 1.0
 * @author khj00
 */


@Getter
public enum ChatRole {
    ADMIN("관리자"),
    USER("유저");

    private final String value;

    ChatRole(String value) {this.value = value;}

    public static List<ChatRole> get() {
        return Arrays.asList(ChatRole.values());
    }
}
