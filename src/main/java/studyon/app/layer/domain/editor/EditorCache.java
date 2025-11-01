package studyon.app.layer.domain.editor;

import lombok.Data;

import java.util.List;

/**
 * 에디터 업로드 파일, 본문 정보를 저장할 캐시 객체
 * @version 1.0
 * @author kcw97
 */

@Data
public class EditorCache {

    private String editorContent; // 에디터 본문
    private List<String> uploadFileNames; // 에디터에 업로드한 누적 파일리스트
}
