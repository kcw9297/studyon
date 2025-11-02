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
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_answer.LectureAnswerDTO;
import studyon.app.layer.domain.lecture_answer.service.LectureAnswerService;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.service.LectureIndexService;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.lecture_question.service.LectureQuestionService;
import studyon.app.layer.domain.lecture_review.LectureReviewDTO;
import studyon.app.layer.domain.lecture_review.service.LectureReviewService;
import studyon.app.layer.domain.lecture_video.LectureVideo;
import studyon.app.layer.domain.lecture_video.LectureVideoDTO;
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
    private final LectureQuestionService lectureQuestionService;
    private final LectureAnswerService lectureAnswerService;
    private final LectureReviewService  lectureReviewService;

    /**
     * [GET] 모든 선생님 정보 가져오기
     * @return 모든 선생님 정보
     */

    @GetMapping
    public ResponseEntity<?> getAllTeachers(@ModelAttribute TeacherDTO.Search rq, Page.Request prq) {
        log.info(" 선생님 전체 정보 GET 요청: 모든 선생님 조회");
        // [1] 모든 선생님 정보 가져와서 리스팅
        Page.Response<TeacherDTO.Read> teachers = teacherService.readPagedList(rq, prq);
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

    // Teacher Profile Part

    @GetMapping("/profile/bestLecture")
    public ResponseEntity<?> getBestLectures(@RequestParam Long teacherId) {
        List<LectureDTO.Read> response = lectureService.readBestLectures(teacherId, 5);
        return RestUtils.ok(response);
    }

    @GetMapping("/profile/recentLecture")
    public ResponseEntity<?> getRecentLectures(@RequestParam Long teacherId) {
        List<LectureDTO.Read> response = lectureService.readRecentLectures(teacherId, 5);
        return RestUtils.ok(response);
    }

    @GetMapping("/reviews/teacher/{teacherId}")
    public ResponseEntity<?> getTeacherReviews(@PathVariable Long teacherId) {
        List<LectureReviewDTO.Read> response = lectureReviewService.readRecentReview(teacherId, 5);
        return RestUtils.ok(response);
    }

    @GetMapping("/profile/detail/{teacherId}")
    public ResponseEntity<?> getTeacherDetail(@PathVariable Long teacherId) {
        TeacherDTO.ReadDetail response = teacherService.readTeacherDetail(teacherId);
        return RestUtils.ok(response);
    }

    // Teacher MyPage PART

    @GetMapping("/management/lecturelist")
    public ResponseEntity<?> getTeacherLectureList(HttpServletRequest request) {
        MemberProfile profile = SessionUtils.getProfile(request.getSession());
        if (profile == null || profile.getTeacherId() == null) {
            return RestUtils.fail(AppStatus.SESSION_EXPIRED);
        }
        Long teacherId = profile.getTeacherId();

        TeacherDTO.LectureListResponse response = teacherService.getLectureListByTeacher(teacherId);
        return RestUtils.ok(response);
    }

    @GetMapping("/management/profile")
    public ResponseEntity<?> getTeacherProfile(HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherMemberId = profile.getMemberId();
        TeacherDTO.TeacherManagementProfile response = teacherService.readProfile(teacherMemberId);
        return RestUtils.ok(response);
    }

    @GetMapping("management/lectureinfo/{lectureId}")
    public ResponseEntity<?> getTeacherLectureInfo(HttpSession session, @PathVariable Long lectureId){
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        LectureDTO.ReadLectureInfo response= lectureService.readLectureInfo(lectureId, teacherId);
        return RestUtils.ok(response);

    }
/*
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
 */

    @PatchMapping("/management/lecture/{lectureId}/thumbnail")
    public ResponseEntity<?> updateLectureThumbnail(
            @PathVariable Long lectureId,
            @RequestPart("file") MultipartFile file,
            HttpSession session
    ) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();

        lectureService.editThumbnail(lectureId, teacherId, file);
        return RestUtils.ok();
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
    @DeleteMapping("/management/lectureIndex/{lectureIndexId}")
    public ResponseEntity<?> deleteLectureIndex(
            @PathVariable Long lectureIndexId,
            HttpSession session
    ) {
        log.info("목차 삭제 Controller 호출");
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        lectureIndexService.deleteIndex(lectureIndexId, teacherId);
        log.info("목차 삭제 Controller 성공");
        return RestUtils.ok();
    }

    @PostMapping("/management/lectureindex/{indexId}/video")
    public ResponseEntity<?> uploadLectureVideo(@PathVariable Long indexId,@RequestParam("file") MultipartFile file) {
        lectureVideoService.uploadVideo(indexId, file);
        return RestUtils.ok("동영상이 업로드 되었습니다.");
    }

    @GetMapping("/management/lectureindex/{indexId}/videos")
    public ResponseEntity<?> getLectureVideos(@PathVariable Long indexId, HttpSession session) {
        Long teacherId = SessionUtils.getMemberId(session);
        List<LectureVideoDTO.Read> videos = lectureVideoService.getVideosForMemberLecture(teacherId, indexId);
        return RestUtils.ok(videos);
    }

    @GetMapping("/management/qna")
    public ResponseEntity<?> getQna(HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        log.info("Session Profile = {}", profile);
        log.info("QNA TEACHERID = {}", teacherId);
        List<LectureQuestionDTO.ReadTeacherQnaDTO> response = lectureQuestionService.getAllQnaList(teacherId);
        return RestUtils.ok(response);
    }

    @GetMapping("/management/qna/detail/{questionId}")
    public ResponseEntity<?> getQnaDetail(@PathVariable Long questionId) {
        LectureQuestionDTO.TeacherQnaDetail response =
                lectureQuestionService.readTeacherQnaDetail(questionId);
        log.info(response.toString());
        return RestUtils.ok(response);
    }

    @GetMapping("/management/qna/answer/{questionId}")
    public ResponseEntity<?> getQnaAnswerPage(@PathVariable Long questionId) {
        LectureQuestionDTO.TeacherQnaDetail response =
                lectureQuestionService.readTeacherQnaDetail(questionId);
        log.info(response.toString());
        return RestUtils.ok(response);
    }

    @PostMapping("/management/qna/answer")
    public ResponseEntity<?> saveAnswer(@ModelAttribute LectureAnswerDTO.Write dto,HttpSession session) {
        log.info("saveAnswer Method ");
        MemberProfile profile = SessionUtils.getProfile(session);
        Long memberId = profile.getMemberId();
        dto.setMemberId(memberId);
        log.info("여기까지는 됨");
        lectureAnswerService.saveAnswer(dto);
        return RestUtils.ok("답변이 등록 되었습니다.");
    }

    @DeleteMapping("/management/qna/deleteQuestion/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        log.info("deleteQuestion Method 호출");
        lectureQuestionService.deleteQuestion(questionId);
        return RestUtils.ok("질문이 성공적으로 삭제되었습니다.");
    }

    @PutMapping("/management/qna/updateQuestion")
    public ResponseEntity<?> updateQuestion(@ModelAttribute LectureAnswerDTO.Write dto) {
        log.info("✅ updateQuestion Method 호출됨 / questionId={}, content={}", dto.getLectureQuestionId(), dto.getContent());
        lectureAnswerService.updateAnswer(dto, dto.getLectureQuestionId());
        return RestUtils.ok();
    }

    @PostMapping("/management/lectureinfo/registerPending")
    public ResponseEntity<?> requestLectureApproval(@RequestParam Long lectureId) {
        log.info("승인요청된 강의 ID: {}", lectureId);
        lectureService.pending(lectureId);
        return RestUtils.ok("강의가 승인 대기 상태로 변경되었습니다.");
    }

    @GetMapping("/management/statistics")
    public ResponseEntity<?> getTeacherStatistics(HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();
        TeacherDTO.TeacherDashboardDTO dashboard = teacherService.getDashboard(teacherId);
        return RestUtils.ok(dashboard);
    }

    // REVIEW용 REST CONTROLLER

    @GetMapping("/management/reviews/lectures")
    public ResponseEntity<?> getTeacherLectures(HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();

        Long memberId = profile.getMemberId();
        List<LectureDTO.Simple> response = lectureService.readLecturesByTeacher(teacherId);
        return RestUtils.ok(response);
    }

    @GetMapping("/management/reviews/{lectureId}")
    public ResponseEntity<?> getLectureReviews(@PathVariable Long lectureId, HttpSession session) {
        MemberProfile profile = SessionUtils.getProfile(session);
        Long teacherId = profile.getTeacherId();

        List<LectureReviewDTO.Read> response = lectureService.readReviewsByLecture(lectureId);
        return RestUtils.ok(response);
    }


}
