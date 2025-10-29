package studyon.app.layer.domain.lecture.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studyon.app.common.constant.Url;
import studyon.app.common.enums.*;
import studyon.app.common.enums.search.LectureKeywordFilter;
import studyon.app.common.enums.search.LectureSort;
import studyon.app.layer.base.dto.Page;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.layer.domain.lecture.LectureDTO;
import studyon.app.layer.domain.lecture.service.LectureService;

/*
 * [수정 이력]
 *  ▶ ver 1.1 (2025-10-20) : khj00 수정
 */

/**
 * 강의 서비스 연결 컨트롤러 클래스 (일단 추천 강의 페이지 중심)
 * @version 1.1
 * @author kcw97
 */


@Slf4j
@Controller
@RequestMapping(Url.LECTURE)
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    /**
     * [GET] 해당하는 과목 추천 페이지
     * @param subject 해당 과목
     * @param count 보여지는 개수 조정을 위한 카운트 변수
     */
    @GetMapping("/recommend/{subject}")
    public String lectureRecommendView(@PathVariable Subject subject, Model model, @RequestParam(defaultValue = "4") int count) {
        // [1] 모델에 변수 바인딩
        model.addAttribute("subject", subject);
        // [2] 뷰 리턴
        return ViewUtils.returnView(model, View.LECTURE,"lecture_recommend");
    }

    @GetMapping("/list")
    public String lectureSearchView(Model model, LectureDTO.Search rq, Page.Request prq) {

        // [1] 필터를 위한 데이터 삽입
        model.addAttribute("subjects", Subject.values());
        model.addAttribute("subjectDetails", SubjectDetail.values());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("filters", LectureKeywordFilter.values());
        model.addAttribute("sorts", LectureSort.values());

        // [2] 검색된 파라미터 중, 선택된 파라미터 삽입
        model.addAttribute("selectedSubjects", rq.getSubjects());
        model.addAttribute("selectedSubjectDetails", rq.getSubjectDetails());
        model.addAttribute("selectedDifficulties", rq.getDifficulties());

        // [3] view 반환 (검색 상세는 비동기로 처리)
        return ViewUtils.returnView(model, View.LECTURE, "lecture_list");

    }
}
