package studyon.app.layer.controller.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
