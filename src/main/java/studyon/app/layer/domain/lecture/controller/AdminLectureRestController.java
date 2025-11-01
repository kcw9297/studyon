package studyon.app.layer.domain.lecture.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.enums.Role;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.RestUtils;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.layer.base.validation.annotation.Title;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;
import studyon.app.layer.domain.lecture_index.LectureIndexDTO;
import studyon.app.layer.domain.lecture_index.service.LectureIndexService;
import studyon.app.layer.domain.member.MemberProfile;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-30) : kcw97 생성
 */

/**
 * 강의 비동기(REST API) 컨트롤러 클래스
 * @version 1.0
 * @author kcw97
 */

@Slf4j
@RestController
@RequestMapping(Url.LECTURES_ADMIN_API)
@RequiredArgsConstructor
@Validated
public class AdminLectureRestController {

    private final LectureService lectureService;

    /**
     * [GET] 강의 목록 검색
     */
    @GetMapping
    public ResponseEntity<?> readPagedList(HttpSession session, LectureDTO.Search rq, Page.Request prq) {

        log.warn("강의 검색 - rq = {}, prq = {}", rq, prq);

        // [1] 검색 회원정보 조회
        MemberProfile profile = SessionUtils.getProfileOrNull(session);
        if (Objects.nonNull(profile)) rq.setSearchTarget(profile.getMemberId(), Role.ROLE_ADMIN); // 회원인 경우, 최근 검색어 삽입을 위한 회원번호 정보 삽입

        // [2] 검색 수행
        Page.Response<LectureDTO.Read> page = lectureService.readPagedList(rq, prq);

        // [3] 검색된 정보 반환
        return RestUtils.ok(page);
    }


    /**
     * [PATCH] 판매(ON_SALE, true) 상태로 변경
     */
    @PatchMapping("/{lectureId:[0-9]+}/start-sale")
    public ResponseEntity<?> startSale(@PathVariable Long lectureId) {

        // [1] 상태 갱신 수행
        lectureService.startSale(lectureId);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_START_SALE);
    }

    /**
     * [PATCH] 판매(NON_SALE, false) 상태로 변경
     */
    @PatchMapping("/{lectureId:[0-9]+}/stop-sale")
    public ResponseEntity<?> stopSale(@PathVariable Long lectureId) {

        // [1] 상태 갱신 수행
        lectureService.stopSale(lectureId);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_STOP_SALE);
    }

    /**
     * [PATCH] 강의 등록 처리
     */
    @PatchMapping("/{lectureId:[0-9]+}/register")
    public ResponseEntity<?> register(@PathVariable Long lectureId) {

        // [1] 상태 갱신 수행
        lectureService.register(lectureId);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_REGISTER);
    }

    /**
     * [PATCH] 강의 반려 처리
     */
    @PatchMapping("/{lectureId:[0-9]+}/reject")
    public ResponseEntity<?> reject(@PathVariable Long lectureId,
                                    @Title(max=30) String rejectReason) {

        // [1] 상태 갱신 수행
        lectureService.reject(lectureId, rejectReason);

        // [2] 성공 응답 반환
        return RestUtils.ok(AppStatus.LECTURE_OK_REJECT);
    }

    /**
     * [GET] 과목별 강의 수 조회
     */

    @GetMapping("/subjectCount")
    public ResponseEntity<?> readSubjectCount() {
        log.info("[API] 과목별 강의 수 조회 요청");
        // [1] 성공 응답 반환
        return RestUtils.ok(lectureService.readLectureCountBySubject());
    }

    /**
     * [GET] 강의 난이도 조회
     */
    
    @GetMapping("/difficultyCount")
    public ResponseEntity<?> readDifficultyCount() {
        log.info("[API] 난이도별 강의 수 조회 요청");
        // [1] 성공 응답 반환
        return RestUtils.ok(lectureService.readLectureCountByDifficulty());
    }

    /**
     * [GET] 강의 등록 상태 조회
     */
    @GetMapping("/statusCount")
    public ResponseEntity<?> readStatusCount() {
        log.info("[API] 등록 상태별 강의 수 조회 요청");
        return RestUtils.ok(lectureService.readLectureCountByStatus());
    }

    /**
     * [GET] 강의 평점 상위 TOP5
     */
    @GetMapping("/topRated")
    public ResponseEntity<?> readTopRated(@RequestParam(defaultValue = "5") int count) {
        Map<String, Double> topLectures = lectureService.readTopRatedLectures(count)
                .stream()
                .collect(Collectors.toMap(
                        LectureDTO.Read::getTitle,        // key 매핑 (dto -> dto.getTitle())
                        LectureDTO.Read::getAverageRate,  // value 매핑 (dto -> dto.getAverageRate())
                         (a, b) -> a,        // key 충돌 시 기존 값 유지
                        LinkedHashMap::new                // Map 구현체 지정 (순서 유지)
                ));
        return RestUtils.ok(topLectures);

                        /*  key 충돌 시 병합 규칙
                        if (keyAlreadyExists) {
                           keep a; // 기존 값 유지
                        }
                         */
    }

    @GetMapping("/targetCount")
    public ResponseEntity<?> readTargetCount() {
        log.info("[API] 대상 학년별 강의 수 조회 요청");
        return RestUtils.ok(lectureService.readLectureCountByTarget());
    }
}
