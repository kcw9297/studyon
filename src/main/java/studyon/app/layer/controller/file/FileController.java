package studyon.app.layer.controller.file;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.URL;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.infra.file.FileManager;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.common.utils.StrUtils;
import studyon.app.layer.domain.file.File;
import studyon.app.layer.domain.file.FileDTO;
import studyon.app.layer.domain.file.repository.FileRepository;
import studyon.app.layer.domain.file.service.FileService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(URL.FILE)
@RequiredArgsConstructor
public class FileController {

    private final FileManager fileManager;
    private final FileService fileService;
    private final FileRepository fileRepository;


    @ResponseBody
    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable Long fileId) {

        // [1] 파일 조회
        FileDTO.Read readDto = fileService.read(fileId);

        // [2] 다운로드를 위한 파일 byte array 추출
        byte[] fileBytes = fileManager.download(readDto.getStoreName(), readDto.getEntity());
        String contentDisposition = "attachment; filename=\"%s\"".formatted(StrUtils.encodeToUTF8(readDto.getOriginalName()));

        // [3] header, body 내 정보 삽입 후 HTTP 응답 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(fileBytes);
    }















    // 테스트 메소드
    @GetMapping("/list.do")
    public String listView(Model model) {

        List<FileDTO.Read> readDtos = fileService.readList();
        log.warn("readDtos = {}", readDtos);
        model.addAttribute("files", readDtos);
        return "layer/files/list";
    }

    // 테스트 메소드
    @GetMapping("/upload.do")
    public String uploadView() {
        return "layer/files/upload";
    }

    @PostMapping("/upload.do")
    public String upload(MultipartFile file) {

        FileDTO.Upload uploadDto = fileManager.upload(file, 1L, Entity.MEMBER, FileType.PROFILE);
        File entity = DTOMapper.toEntity(uploadDto);
        fileRepository.save(entity);

        log.warn("uploadFileDTO : {}, File Entity : {}", uploadDto, entity);
        return "redirect:/file/list.do";
    }

    // 테스트 메소드
    @PostMapping("/upload_all.do")
    public String uploadAll(List<MultipartFile> files) {

        List<FileDTO.Upload> dtos = files.stream()
                .map(file -> fileManager.upload(file, 1L, Entity.MEMBER, FileType.PROFILE))
                .toList();

        log.warn("uploadFileDTOs : {}", dtos);
        return "redirect:/file/list.do";
    }


    // 테스트 메소드
    @ResponseBody
    @DeleteMapping("/api/{fileId}")
    public String remove(@PathVariable Long fileId) {

        // [1] 파일 조회
        FileDTO.Read readDto = fileService.read(fileId);

        fileManager.remove(readDto.getStoreName(), readDto.getEntity());
        return "OK";
    }

}
