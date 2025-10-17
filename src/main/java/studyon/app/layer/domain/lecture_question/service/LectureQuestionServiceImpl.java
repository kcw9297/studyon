package studyon.app.layer.domain.lecture_question.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.lecture_question.repository.LectureQuestionRepository;

import java.util.List;
import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 질문 서비스 구현 클래스
 * @version 1.0
 * @author khj00
 */

@Service
@RequiredArgsConstructor
@Transactional
public class LectureQuestionServiceImpl implements LectureQuestionService {

    private final LectureQuestionRepository lectureQuestionRepository;

    @Override
    public List<LectureQuestionDTO.Read> getAllQuestions() {
        return lectureQuestionRepository.findAll()
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }

    @Override
    public Optional<LectureQuestionDTO.Read> getQuestion(Long id) {
        return Optional.empty();
    }

    @Override
    public LectureQuestionDTO.Read createQuestion(LectureQuestionDTO.Write dto) {
        return null;
    }

    @Override
    public void deleteQuestion(Long id) {

    }
}
