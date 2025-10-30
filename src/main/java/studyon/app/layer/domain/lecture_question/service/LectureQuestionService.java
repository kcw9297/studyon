package studyon.app.layer.domain.lecture_question.service;

import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;

import java.util.List;
import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 질문 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureQuestionService {
    /** 전체 질문 목록 조회 */
    List<LectureQuestionDTO.Read> readAllQuestions();
    /** 특정 질문 조회 */
    Optional<LectureQuestionDTO.Read> readQuestion(Long id);

    /** 질문 등록 */
    LectureQuestionDTO.Read createQuestion(LectureQuestionDTO.Write dto);

    /** 질문 삭제 */
    void deleteQuestion(Long id);
    void register(LectureQuestionDTO.Write rq);
    List<LectureQuestionDTO.ReadQna> readQuestionAndAnswer(Long lectureId, Long lectureIndexId);
    List<LectureQuestionDTO.ReadTeacherQnaDTO> getAllQnaList(Long teacherId);
    LectureQuestionDTO.TeacherQnaDetail readTeacherQnaDetail(Long lectureQuestionId);
}
