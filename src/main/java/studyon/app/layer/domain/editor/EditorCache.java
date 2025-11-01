package studyon.app.layer.domain.editor;

import lombok.AllArgsConstructor;
import lombok.Data;
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

    private List<FileDTO.Upload> uploadFiles; // 업로드한 누적 파일리스트
    private List<FileDTO.Remove> removeFiles; // 에디터에 업로드한 누적 파일리스트

    public EditorCache() {
        this.uploadFiles = new ArrayList<>();
        this.removeFiles = new ArrayList<>();
    }

    public void addUploadFile(FileDTO.Upload uploadFile) {
        uploadFiles.add(uploadFile);
    }

    public void setAllFiles(List<FileDTO.Upload> uploadFiles, List<FileDTO.Remove> removeFiles) {
        this.uploadFiles = uploadFiles;
        this.removeFiles = removeFiles;
    }

    public void clearAll() {
        uploadFiles.clear();
        removeFiles.clear();
    }
}
