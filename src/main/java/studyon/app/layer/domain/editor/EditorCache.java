package studyon.app.layer.domain.editor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import studyon.app.layer.domain.file.FileDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 에디터 업로드 파일, 본문 정보를 저장할 캐시 객체
 * @version 1.0
 * @author kcw97
 */

@Data
@AllArgsConstructor
public class EditorCache {

    private List<FileDTO.Upload> uploadFiles; // 에디터 본문
    private List<String> uploadFileNames; // 에디터에 업로드한 누적 파일리스트

    public EditorCache() {
        this.uploadFiles = new ArrayList<>();
        this.uploadFileNames = new ArrayList<>();
    }

    public void addFile(FileDTO.Upload uploadFile, String uploadFileName) {
        this.uploadFiles.add(uploadFile);
        this.uploadFileNames.add(uploadFileName);
    }
}
