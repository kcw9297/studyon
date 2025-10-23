package studyon.app.layer.domain.test;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Action;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.enums.View;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;

import java.util.*;

@Transactional
@Slf4j
@Controller
@RequestMapping("/testboard")
@RequiredArgsConstructor
public class TestBoardController {

    private final TestBoardRepository repository;
    private final FileManager fileManager;
    private final CacheManager cacheManager;
    private final FileRepository fileRepository;

    @GetMapping("/write")
    public String write(HttpSession session, Model model) {

        // [1] 캐시 조회
        TestCache writeCache =
                cacheManager.getOrRecordCache(Entity.LECTURE_QUESTION.name(), Action.WRITE.name(), session.getId(), TestCache.class);

        log.warn("[TestBoardController][write] - writeCache = {}", writeCache);
        if (Objects.nonNull(writeCache)) model.addAttribute("data", writeCache);
        return ViewUtils.returnNoFrameView(View.TEST, "write");
    }

    @GetMapping("/edit/{id}")
    public String edit(HttpSession session, Model model, @PathVariable Long id) {

        // [1] 캐시 조회
        TestCache writeCache =
                cacheManager.getCacheAndDeleteOldCache(Entity.LECTURE_QUESTION.name(), Action.EDIT.name(), id, session.getId(), TestCache.class);

        log.warn("[TestBoardController][edit] - editCache = {}", writeCache);
        if (Objects.nonNull(writeCache)) model.addAttribute("data", writeCache);
        else {
            TestBoard testBoard = repository.findById(id).orElse(new TestBoard());
            log.info("[TestBoardController][edit] - testBoard = {}", testBoard);
            model.addAttribute("data", testBoard);
            cacheManager.updateCache(Entity.LECTURE_QUESTION.name(), Action.EDIT.name(), id, session.getId(), new TestCache(testBoard.getContent(), new ArrayList<>()));
        }

        model.addAttribute("id", id);
        return ViewUtils.returnNoFrameView(View.TEST, "edit");
    }


    @ResponseBody
    @PutMapping("/cache")
    public ResponseEntity<?> updateCache(HttpSession session, String content, Action action) {

        // 현재 캐시 조회
        TestCache cache =
                cacheManager.getCache(Entity.LECTURE_QUESTION.name(), action.name(), session.getId(), TestCache.class);


        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);


        // 갱신
        log.warn("[TestBoardController][updateCache] - image count = {}, action = {}", cache.getUploadedImages().size(), action);
        cache.setContent(StrUtils.purifyHtml(content));
        cacheManager.updateCache(Entity.LECTURE_QUESTION.name(), action.name(), session.getId(), cache);
        return ResponseEntity.ok().build();
    }


    @ResponseBody
    @PutMapping("/{id}/cache")
    public ResponseEntity<?> updateCache(HttpSession session, @PathVariable Long id, String content, Action action) {

        // 현재 캐시 조회
        TestCache cache =
                cacheManager.getCache(Entity.LECTURE_QUESTION.name(), action.name(), id, session.getId(), TestCache.class);

        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);

        // 갱신
        log.warn("[TestBoardController][updateCache] - image count = {}, action = {}", cache.getUploadedImages().size(), action);
        cache.setContent(StrUtils.purifyHtml(content));
        cacheManager.updateCache(Entity.LECTURE_QUESTION.name(), action.name(), session.getId(), cache);
        return ResponseEntity.ok().build();
    }


    @ResponseBody
    @PostMapping("/editor_file")
    public ResponseEntity<?> uploadEditorFile(HttpSession session, String content, MultipartFile file) {
        return doUploadEditorFile(session, null, content, file, Action.WRITE);
    }

    @ResponseBody
    @PostMapping("/{id}/editor_file")
    public ResponseEntity<?> uploadEditorFile(HttpSession session, @PathVariable Long id, String content, MultipartFile file, Action action) {
        return doUploadEditorFile(session, id, content, file, action);
    }


    private ResponseEntity<?> doUploadEditorFile(HttpSession session, Long id, String content, MultipartFile file, Action action) {
        // [1] 캐시 조회
        TestCache cache =
                cacheManager.getCache(Entity.LECTURE_QUESTION.name(), action.name(), session.getId(), TestCache.class);

        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);

        // [2] 파일 업로드
        FileDTO.Upload fileUpload = fileManager.upload(file, id, Entity.LECTURE_QUESTION, FileType.EDITOR);
        log.warn("fileUpload = {}, sessionId = {}", fileUpload, session.getId());

        // 캐시 데이터 검증 전 현재 캐시정보 내 이미지 확인
        cache.setContent(StrUtils.purifyHtml(content));
        cache.getUploadedImages().add(fileUpload);

        // [3] 업로드된 임시 파일 주소 반환
        cacheManager.updateCache(Entity.LECTURE_QUESTION.name(), action.name(), session.getId(), cache);
        return RestUtils.ok(Map.of("url", fileUpload.getFilePath()));
    }


    @ResponseBody
    @PostMapping
    public ResponseEntity<?> write(HttpSession session, String content) {

        // [1] Cache 조회
        // 현재 캐시 조회
        TestCache cache =
                cacheManager.getCache(Entity.LECTURE_QUESTION.name(), Action.WRITE.name(), session.getId(), TestCache.class);

        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);

        // [2] HTML Content 정화 후, 게시글 저장
        TestBoard testBoard = TestBoard.builder().content(StrUtils.purifyHtml(content)).build();
        repository.save(testBoard);


        // [3] 실제로 업로드된 파일정보 DB 저장
        Set<String> currentImages = new HashSet<>(StrUtils.purifyAndExtractFileNameFromHtml(content));
        List<FileDTO.Upload> uploadedImages = cache.getUploadedImages();

        List<File> usedImageFiles = uploadedImages.stream()
                .filter(file -> currentImages.contains(file.getStoreName()))
                .peek(file -> file.setEntityId(testBoard.getId())) // id 삽입
                .map(DTOMapper::toEntity)
                .toList();

        fileRepository.saveAll(usedImageFiles);


        // [4] 실제 업로드되지 않은 파일은 물리적 삭제 수행
        uploadedImages.stream()
                .filter(dto -> !currentImages.contains(dto.getStoreName()))
                .forEach(dto -> fileManager.remove(dto.getStoreName(), dto.getEntity()));


        // [5] 캐시 삭제 후 성공 처리
        cacheManager.removeCacheAndBackup(Entity.LECTURE_QUESTION.name(), Action.WRITE.name(), session.getId());
        return RestUtils.ok(Rest.Message.of("글 작성 성공"), URL.INDEX);
    }


    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(HttpSession session, @PathVariable Long id, String content) {

        // [1] Cache 조회
        // 현재 캐시 조회
        TestCache cache = cacheManager.getCacheAndDeleteOldCache(
                Entity.LECTURE_QUESTION.name(),
                Action.EDIT.name(),
                id,
                session.getId(),
                TestCache.class
        );

        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);

        // [2] HTML Content 정화 후, 게시글 저장
        TestBoard testBoard = repository.findById(id).orElse(new TestBoard());
        testBoard.editContent(StrUtils.purifyHtml(content));

        // [3] 실제로 업로드된 파일정보 DB 저장
        List<File> AllFiles = fileRepository.findAllByEntityId(id);
        Set<String> currentImages = new HashSet<>(StrUtils.purifyAndExtractFileNameFromHtml(content));
        List<FileDTO.Upload> uploadedImages = cache.getUploadedImages();
        log.warn("AllFilesLen = {}, currentImagesLen = {}, uploadedImagesLen = {}",
                AllFiles.size(), currentImages.size(), uploadedImages.size());

        // 새롭게 업로드한 파일
        List<File> newImageFiles = uploadedImages.stream()
                .filter(file -> currentImages.contains(file.getStoreName()))
                .map(DTOMapper::toEntity)
                .toList();

        fileRepository.saveAll(newImageFiles);


        // 삭제된 파일 조회
        List<File> files =
                AllFiles.stream()
                        .filter(file -> !currentImages.contains(file.getStoreName()))
                        .toList();

        // db 내에서 삭제
        fileRepository.deleteAllById(files.stream().map(File::getFileId).toList());

        // 물리적 삭제
        files.forEach(entity -> fileManager.remove(entity.getStoreName(), entity.getEntity()));


        // [5] 캐시 삭제 후 성공 처리
        cacheManager.removeCacheAndBackup(Entity.LECTURE_QUESTION.name(), Action.EDIT.name(), session.getId());
        return RestUtils.ok(Rest.Message.of("글 수정 성공"), URL.INDEX);
    }
}
