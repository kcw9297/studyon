package studyon.app.layer.domain.lecture_answer.service;

import studyon.app.layer.domain.lecture_answer.LectureAnswerDTO;

/**
 * 강의 답변 관련 서비스 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface LectureAnswerService {
    public void saveAnswer(LectureAnswerDTO.Write rq);
    public void updateAnswer(LectureAnswerDTO.Write dto,Long questionId);
}
