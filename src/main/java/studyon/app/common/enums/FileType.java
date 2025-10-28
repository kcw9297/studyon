package studyon.app.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 파일 유형
 * @version 1.1
 * @author kcw97
 *
 * khs97 video 추가
 */
@Getter
public enum FileType {

    THUMBNAIL("thumbnail"),
    EDITOR("editor"),
    PROFILE("editor"),
    VIDEO("video"),
    UPLOAD("upload");

    private final String value;

    FileType(String value) {this.value = value;}

    public static List<FileType> get() {
        return Arrays.asList(FileType.values());
    }
}
