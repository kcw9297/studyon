package studyon.app.layer.controller.test;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.common.enums.View;
import studyon.app.common.utils.StrUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
                cacheManager.getOrRecordCache(Entity.LECTURE_QUESTION.getName(), session.getId(), TestCache.class);

        log.warn("[TestBoardController][write] - writeCache = {}", writeCache);
        if (Objects.nonNull(writeCache)) model.addAttribute("cache", writeCache);
        return ViewUtils.returnNoFrameView(View.TEST, "write");
    }


    @ResponseBody
    @PutMapping("/cache")
    public ResponseEntity<?> updateCache(HttpSession session, String content) {

        // 현재 캐시 조회
        TestCache cache =
                cacheManager.getCache(Entity.LECTURE_QUESTION.getName(), session.getId(), TestCache.class);

        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);

        // 캐시 데이터 검증 전 현재 캐시정보 내 이미지 확인
        String purified = StrUtils.purifyHtml(content);

        // 확인
        log.warn("[TestBoardController][cache] - content = {}, purified = {}", content, purified);
        cacheManager.updateCache(Entity.LECTURE_QUESTION.getName(), session.getId(), new TestCache(purified, cache.getUploadedImages()));
        return ResponseEntity.ok().build();
    }


    @ResponseBody
    @PostMapping("/editor_file")
    public ResponseEntity<?> uploadEditorFile(HttpSession session, String content, MultipartFile file) {

        // [1] 캐시 조회
        TestCache cache =
                cacheManager.getCache(Entity.LECTURE_QUESTION.getName(), session.getId(), TestCache.class);

        // 캐시가 없으면 실패 응답
        if (Objects.isNull(cache))
            return RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);

        // [2] 파일 업로
        FileDTO.Upload fileUpload = fileManager.upload(file, null, Entity.LECTURE_QUESTION, FileType.EDITOR);
        log.warn("fileUpload = {}, sessionId = {}", fileUpload, session.getId());

        // 캐시 데이터 검증 전 현재 캐시정보 내 이미지 확인
        String purified = StrUtils.purifyHtml(content);
        List<String> currentImages = StrUtils.purifyAndExtractImgSrcFromHtml(content);
        Set<String> uploadedImages = cache.getUploadedImages();

        // 새롭게 추가된 이미지
        List<String> existingImages = currentImages.stream()
                .filter(uploadedImages::contains)
                .toList();

        uploadedImages.addAll(currentImages);

        // 확인
        log.warn("[TestBoardController][upload] - currentImages = {}", currentImages);
        log.warn("[TestBoardController][upload] - content = {}, purified = {}", content, purified);
        log.warn("[TestBoardController][upload] - existingImages = {}, uploadedImages = {}", existingImages, uploadedImages);


        // [3] 업로드된 임시 파일 주소 반환
        cacheManager.updateCache(Entity.LECTURE_QUESTION.getName(), session.getId(), new TestCache(purified, uploadedImages));
        return RestUtils.ok(Map.of("url", fileUpload.getFilePath()));
    }


    @ResponseBody
    @PostMapping
    public ResponseEntity<?> write(HttpSession session, String content) {

        TestBoard testBoard = TestBoard.builder().content(content).build();
        repository.save(testBoard);

        log.warn("testBoard = {}", testBoard);
        cacheManager.removeCache(Entity.LECTURE_QUESTION.getName(), session.getId());
        return RestUtils.ok(Rest.Message.of("글 작성 성공"), URL.INDEX);
    }

}
