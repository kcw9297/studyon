package studyon.app.layer.domain.lecture_answer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;
import studyon.app.layer.domain.lecture_answer.LectureAnswerDTO;
import studyon.app.layer.domain.lecture_answer.repository.LectureAnswerRepository;
import studyon.app.layer.domain.lecture_question.LectureQuestion;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.lecture_question.repository.LectureQuestionRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureAnswerServiceImpl implements LectureAnswerService {

    private final LectureAnswerRepository lectureAnswerRepository;
    private final LectureQuestionRepository lectureQuestionRepository;
    private final MemberRepository memberRepository;

    @Override
    public void saveAnswer(LectureAnswerDTO.Write dto) {
        LectureQuestion question = lectureQuestionRepository.findById(dto.getLectureQuestionId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.QUESTION_NOT_FOUND));

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));

        LectureAnswer entity = LectureAnswer.builder()
                .content(dto.getContent())
                .member(member)
                .build();

        // 1️⃣ 답변 저장
        LectureAnswer savedAnswer = lectureAnswerRepository.save(entity);

        // 2️⃣ 질문에 답변 연결
        question.setLectureAnswer(savedAnswer);
        question.updateQuestion(question.getTitle(), question.getContent(), true);

        // 3️⃣ 질문 업데이트 (FK 저장됨)
        lectureQuestionRepository.save(question);

        log.info("✅ 답변 저장 및 연결 완료: Q={} / A={}",
                question.getLectureQuestionId(), savedAnswer.getLectureAnswerId());
    }

    @Override
    public void updateAnswer(LectureAnswerDTO.Write dto, Long questionId) {
            LectureQuestion question = lectureQuestionRepository.findById(questionId)
                    .orElseThrow(() -> new BusinessLogicException(AppStatus.QUESTION_NOT_FOUND));
            LectureAnswer answer = question.getLectureAnswer();
        lectureAnswerRepository.updateAnswerContent(answer.getLectureAnswerId(), dto.getContent());

            log.info("답변 수정 완료");

    }

}
