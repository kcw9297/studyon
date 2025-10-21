package studyon.app.layer.controller.test;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.View;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.dto.Rest;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.ViewUtils;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/testboard")
@RequiredArgsConstructor
public class TestBoardController {

    private final TestBoardRepository repository;
    private final FileManager fileManager;
    private final CacheManager cacheManager;

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
    @PostMapping("/change")
    public ResponseEntity<?> change(HttpSession session, Long entityId, String content) {

        log.warn("[TestBoardController][write] - entityId = {}, content = {}", entityId, content);
        TestCache testCache = new TestCache(entityId, content);
        boolean result;

        if (Objects.isNull(entityId))
            result = cacheManager.updateCache(Entity.LECTURE_QUESTION.getName(), session.getId(), testCache);

        else
            result = cacheManager.updateCache(Entity.LECTURE_QUESTION.getName(), session.getId(), entityId, testCache);

        return result ?
                ResponseEntity.ok().build() :
                RestUtils.fail400(Rest.Message.of("세션이 만료되었습니다\n다시 시도해 주세요"), URL.INDEX);
    }

    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity<?> write(HttpSession session, String content) {

        TestBoard testBoard = TestBoard.builder().content(content).build();
        repository.save(testBoard);

        log.warn("testBoard = {}", testBoard);
        cacheManager.removeCache(Entity.LECTURE_QUESTION.getName(), session.getId());
        return RestUtils.ok(Rest.Message.of("글 작성 성공"), URL.INDEX);
    }

}
