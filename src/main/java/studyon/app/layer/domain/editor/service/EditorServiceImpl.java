package studyon.app.layer.domain.editor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.infra.cache.manager.EditorCacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.editor.EditorCache;
import studyon.app.layer.domain.file.FileDTO;

import java.util.Objects;

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
    private final FileManager fileManager;


    @Override
    public void recordEditorCache(String id) {
        editorCacheManager.recordEditorCache(id, new EditorCache());
    }

    @Override
    public String uploadEditorImage(String id, Entity entity, MultipartFile uploadImageFile) {

        // [1] file 등록
        FileDTO.Upload uploadFile = DTOMapper.toUploadDTO(uploadImageFile, null, entity, FileType.EDITOR);
        String filePath = fileManager.upload(uploadImageFile, uploadFile.getStoreName(), entity.getName());

        // [2] 에디터 캐시정보 조회 후 갱신
        EditorCache editorCache = editorCacheManager.getEditorCache(id, EditorCache.class);

        // 캐시가 만료되어 등록할 수 없는 경우
        if (Objects.isNull(editorCache)) throw new BusinessLogicException(AppStatus.EDITOR_CACHE_NOT_EXIST);

        // [3] 새롭게 등록한 파일 저장
        editorCache.addUploadFile(uploadFile);
        editorCacheManager.updateEditorCache(id, editorCache);

        // [4] 새롭게 저장된 파일경로 변환
        return filePath;
    }
}
