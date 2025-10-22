package studyon.app.layer.controller.test;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TestCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String content = "";
    private Set<String> uploadedImages = new HashSet<>(); // 에디터에 누적으로 업로드되었던 이미지들 (주소)
}
