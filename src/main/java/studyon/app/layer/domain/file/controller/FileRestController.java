package studyon.app.layer.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;


@Slf4j
@RestController
@RequiredArgsConstructor
public class FileRestController {

    private final FileManager fileManager;
    private final CacheManager cacheManager;

    /*
    @PostMapping("/api/files/editor")
    public ResponseEntity<?> uploadEditor(MultipartFile file) {

        // [1]
        fileManager.upl


        // [2]

        // [3]
    }

*/


}
