package studyon.app.layer.domain.lecture_answer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;
import studyon.app.layer.domain.lecture_answer.LectureAnswerDTO;
import studyon.app.layer.domain.lecture_answer.repository.LectureAnswerRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureAnswerServiceImpl implements LectureAnswerService {

    private final LectureAnswerRepository lectureAnswerRepository;

    @Override
    public void saveAnswer(LectureAnswerDTO.Write rq) {
        LectureAnswer entity = LectureAnswer.builder()
                .content(rq.getContent())
                .build();
        lectureAnswerRepository.save(entity);
    }
}
