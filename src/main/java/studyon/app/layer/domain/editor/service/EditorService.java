package studyon.app.layer.domain.editor.service;

import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.Entity;

/**
 * 에디터 관련 핵심 비즈니스 로직을 제공하는 클래스
 * @version 1.0
 * @author kcw97
 */
public interface EditorService {

    void recordEditorCache(String id);

    String uploadEditorImage(String id, Entity entity, MultipartFile uploadImageFile);

}
