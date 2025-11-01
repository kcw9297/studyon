package studyon.app.layer.domain.editor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.infra.cache.manager.EditorCacheManager;

/**
 * 에디터 서비스 구현체
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EditorServiceImpl implements EditorService {

    private final EditorCacheManager editorCacheManager;

    @Override
    public void uploadEditorImage(String sessionId, String editorContent, MultipartFile uploadImageFile) {

        // [1] file
    }

    @Override
    public void updateContent(String sessionId, String editorContent) {

    }

    @Override
    public void removeOrphanImage(String sessionId) {

    }
}
