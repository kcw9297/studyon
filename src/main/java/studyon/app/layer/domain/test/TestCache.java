package studyon.app.layer.domain.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import studyon.app.layer.domain.file.FileDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TestCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String content = "";
    private List<FileDTO.Upload> uploadedImages = new ArrayList<>(); // 에디터에 누적으로 업로드되었던 이미지들 (주소)
}
