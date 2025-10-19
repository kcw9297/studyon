package studyon.app.layer.controller.editor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Entity;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.HttpUtils;

/**
 * Summernote 내 업로드된 이미지를 처리하는 컨트롤러 크랠스
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class EditorRestController {

    private final CacheManager cacheManager;

    @PostMapping(URL.API_EDITOR_UPLOAD)
    public ResponseEntity<?> uploadCache(MultipartFile file, Entity entity) {
        //cacheManager.recordWriteLectureQuestion();
        log.warn("file = {}", file);
        return HttpUtils.ok(Rest.Message.of("이미지 등록 성공"));
    }


    @PostMapping("/api/editor/write")
    public ResponseEntity<?> write(String content) {
        log.warn("content = {}", content);
        return HttpUtils.ok(Rest.Message.of("글 작성 성공"));
    }
}
