package studyon.app.layer.domain.teacher.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.LectureRegisterStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.service.LectureIndexService;
import studyon.app.layer.domain.lecture_video.LectureVideo;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
import studyon.app.layer.domain.lecture_video.repository.LectureVideoRepository;
import studyon.app.layer.domain.lecture_video.service.LectureVideoService;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.TeacherDTO;
import studyon.app.layer.domain.teacher.service.TeacherService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-21) : khj00 최초 작성
 */

/**
 * 선생님 JSON 데이터 불러오는 컨트롤러 클래스
 * @version 1.0
 * @author khj00
 */

@Slf4j
@RestController
@RequestMapping(Url.TEACHERS_API)
@RequiredArgsConstructor
public class TeacherRestController {

    // [0] 선생님 서비스 불러오기
    private final TeacherService teacherService;
    private final LectureService lectureService;
    private final CacheManager cacheManager;
    private final LectureIndexService lectureIndexService;
    private final LectureVideoService lectureVideoService;

    /**
     * [GET] 모든 선생님 정보 가져오기
     * @return 모든 선생님 정보
     */

    @GetMapping
    public ResponseEntity<?> getAllTeachers(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" 선생님 전체 정보 GET 요청: 모든 선생님 조회");
        // [1] 모든 선생님 정보 가져와서 리스팅
        List<TeacherDTO.Read> teachers = teacherService.readAllTeachers();
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(teachers);
    }

    /**
     * [GET] 과목별 선생님 정보 가져오기
     * @return 해당 과목 선생님들 정보
     */
    @GetMapping( "/subject/{subject}")
    public ResponseEntity<?> getTeachersBySubject(@ModelAttribute TeacherDTO.Search rq) {
        log.info(" GET 요청: 과목 [{}]의 선생님 조회", rq.getSubject());
        // [1] 과목별로 선생님 정보 가져와서 리스팅
        List<TeacherDTO.Read> teachersBySubject = teacherService.readTeachersBySubject(rq.getSubject());
        // [2] 리스팅한 정보 리턴하기
        return RestUtils.ok(teachersBySubject);
    }

    @GetMapping("/management/lecturelist")
    public ResponseEntity<?> getTeacherLectureList(HttpServletRequest request) {
        // ✅ 세션 프로필 꺼내기
        MemberProfile profile = SessionUtils.getProfile(request.getSession());

        if (profile == null || profile.getTeacherId() == null) {
            log.warn("⚠ 세션에 teacher 정보가 없습니다.");
            return RestUtils.fail(AppStatus.SESSION_EXPIRED);
        }
        Long teacherId = profile.getTeacherId();
        log.info("✅ 로그인한 강사 ID = {}", teacherId);

        TeacherDTO.LectureListResponse response = teacherService.getLectureListByTeacher(teacherId);
        return RestUtils.ok(response);
    }

    @PostMapping("lecture/register")
    public ResponseEntity<?> registerLecture(LectureDTO.Register dto, LectureRegisterStatus status, HttpSession session){
        log.info("강의 등록 요청");
        log.info(dto.toString());
        MemberProfile profile = SessionUtils.getProfile(session);
        lectureService.registerLecture(dto, profile, status);
        return RestUtils.ok("강의가 등록되었습니다.");
    }

    @GetMapping("/management/profile")
    public ResponseEntity<?> getTeacherProfile(HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherMemberId = profile.getMemberId();
        TeacherDTO.TeacherManagementProfile response = teacherService.readProfile(teacherMemberId);
        return RestUtils.ok(response);

        //가져올 정보 : 강사명, 강사 이메일, 강사 프로필, 강의 수, 수강생 수, 평균 평점
    }

    @GetMapping("management/lectureinfo/{lectureId}")
    public ResponseEntity<?> getTeacherLectureInfo(HttpSession session, @PathVariable Long lectureId){
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        LectureDTO.ReadLectureInfo response= lectureService.readLectureInfo(lectureId, teacherId);
        return RestUtils.ok(response);

    }

    @GetMapping("/management/profile/image")
    public ResponseEntity<Resource> getTeacherProfileImage(HttpSession session) {
        // [1] 세션에서 프로필 정보 가져오기
        MemberProfile profile = SessionUtils.getProfile(session);

        if (profile == null || profile.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // [2] 실제 파일 경로 (C:PROJECT_FILE/member/xxxx.jpg)
            String filePath = "C:/PROJECT_FILE/" + profile.getProfileImage().getFilePath();
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            // [3] 파일 리소스 생성
            Resource resource = new UrlResource(path.toUri());

            // [4] Content-Type 지정
            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "image/jpeg";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/management/lecture/{lectureId}/thumbnail")
    public ResponseEntity<?> updateLectureThumbnail(
            @PathVariable Long lectureId,
            @RequestPart("file") MultipartFile file,
            HttpSession session
    ) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();

        lectureService.updateThumbnail(lectureId, teacherId, file);

        return RestUtils.ok("썸네일이 등록되었습니다.");
    }

    @GetMapping("/management/lecture/{lectureId}/thumbnail/view")
    public ResponseEntity<Resource> getLectureThumbnail(
            @PathVariable Long lectureId
    ) {
        try {
            // 1️⃣ 썸네일 파일 경로 조회
            String filePath = lectureService.getLectureThumbnailPath(lectureId); // 아래에 설명

            if (filePath == null) {
                return ResponseEntity.notFound().build();
            }

            // 2️⃣ 실제 경로 변환
            Path path = Paths.get("C:/PROJECT_FILE/" + filePath);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            // 3️⃣ 리소스 생성
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "image/jpeg";

            // 4️⃣ 응답 반환
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * [GET] 특정 강의의 목차 전체 조회
     */
    @GetMapping("/management/lectureindex/{lectureId}")
    public ResponseEntity<?> getLectureIndexes(@PathVariable Long lectureId, HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        List<LectureIndexDTO.Read> response = lectureIndexService.readAllByLectureId(lectureId, teacherId);
        return RestUtils.ok(response);
    }

    /**
     * [POST] 특정 강의에 목차 추가
     */
    @PostMapping("/management/lectureindex/{lectureId}")
    public ResponseEntity<?> createLectureIndex(
            @PathVariable Long lectureId,
            @RequestBody LectureIndexDTO.Write dto,
            HttpSession session
    ) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        lectureIndexService.createIndex(lectureId, teacherId, dto);
        return RestUtils.ok("강의 목차가 추가되었습니다.");
    }

    /**
     * [PUT] 특정 강의의 목차 전체 수정 (예: 순서 변경)
     */
    @PutMapping("/management/lectureindex/{lectureId}")
    public ResponseEntity<?> updateLectureIndexes(
            @PathVariable Long lectureId,
            @RequestBody List<LectureIndexDTO.Edit> dtos,
            HttpSession session
    ) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        lectureIndexService.updateIndexes(lectureId, teacherId, dtos);
        return RestUtils.ok("강의 목차가 수정되었습니다.");
    }

    /**
     * [DELETE] 특정 목차 삭제
     */
    @DeleteMapping("/management/lectureindex/{lectureIndexId}")
    public ResponseEntity<?> deleteLectureIndex(
            @PathVariable Long lectureIndexId,
            HttpSession session
    ) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        lectureIndexService.deleteIndex(lectureIndexId, teacherId);
        return RestUtils.ok("강의 목차가 삭제되었습니다.");
    }


    @PostMapping("/management/lectureindex/{indexId}/video")
    public ResponseEntity<?> uploadLectureVideo(@PathVariable Long indexId,@RequestParam("file") MultipartFile file) {
        lectureVideoService.uploadVideo(indexId, file);
        return RestUtils.ok("동영상이 업로드 되었습니다.");
    }

//    @GetMapping("/management/lectureindex/{lectureId}/videos")
//    public ResponseEntity<?> getLectureVideos(@PathVariable Long lectureId,HttpSession session) {
//        // 🔥 로그인한 강사 ID 가져오기 (세션 or SecurityContext)
//        Long teacherId = SessionUtils.getMemberId(session);
//
//        List<LectureVideoDTO.Read> list =
//                lectureVideoService.getVideosForMemberLecture(teacherId, lectureId);
//
//        return RestUtils.ok(list);
//    }

    @GetMapping("/management/lectureindex/{indexId}/videos")
    public ResponseEntity<?> getLectureVideos(@PathVariable Long indexId, HttpSession session) {
        Long teacherId = SessionUtils.getMemberId(session);
        List<LectureVideoDTO.Read> videos = lectureVideoService.getVideosForMemberLecture(teacherId, indexId);
        return RestUtils.ok(videos);
    }



}
