package studyon.app.layer.domain.lecture_question.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import studyon.app.common.enums.AppStatus;
import studyon.app.common.exception.BusinessLogicException;
import studyon.app.layer.base.utils.DTOMapper;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;
import studyon.app.layer.domain.lecture_answer.LectureAnswer;
import studyon.app.layer.domain.lecture_answer.repository.LectureAnswerRepository;
import studyon.app.layer.domain.lecture_index.LectureIndex;
import studyon.app.layer.domain.lecture_index.repository.LectureIndexRepository;
import studyon.app.layer.domain.lecture_question.LectureQuestion;
import studyon.app.layer.domain.lecture_question.LectureQuestionDTO;
import studyon.app.layer.domain.lecture_question.repository.LectureQuestionRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;
import studyon.app.layer.domain.teacher.TeacherDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 질문 서비스 인터페이스 구현 클래스
 * @version 1.0
 * @author khj00
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureQuestionServiceImpl implements LectureQuestionService {
    private final LectureAnswerRepository lectureAnswerRepository;
    private final LectureQuestionRepository lectureQuestionRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;
    private final LectureIndexRepository  lectureIndexRepository;

    @Override
    public List<LectureQuestionDTO.Read> readAllQuestions() {
        return lectureQuestionRepository.findAll()
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }

    @Override
    public Optional<LectureQuestionDTO.Read> readQuestion(Long id) {
        return Optional.empty();
    }

    @Override
    public LectureQuestionDTO.Read createQuestion(LectureQuestionDTO.Write dto) {
        return null;
    }

    @Override
    public void deleteQuestion(Long id) {

    }

    @Override
    public void register(LectureQuestionDTO.Write rq) {

        Lecture lecture = lectureRepository.findById(rq.getLectureId()).orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));
        Member member = memberRepository.findById(rq.getMemberId()).orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
        LectureIndex lectureIndex = lectureIndexRepository.findById(rq.getLectureIndexId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        log.info("register service 실행");
        LectureQuestion entity = LectureQuestion.builder()
                .title(rq.getTitle())
                .content(rq.getContent())
                .lecture(lecture)
                .lectureIndex(lectureIndex)
                .isSolved(false)
                .member(member)
                .build();

        lectureQuestionRepository.save(entity);
        log.info("register service 완료");

    }

    @Override
    @Transactional
    public List<LectureQuestionDTO.ReadQna> readQuestionAndAnswer(Long lectureId, Long lectureIndexId) {

        // 1️⃣ 질문 목록 조회
        List<LectureQuestion> questions =
                lectureQuestionRepository.findByLecture_LectureIdAndLectureIndex_LectureIndexId(lectureId, lectureIndexId);

        List<LectureQuestionDTO.ReadQna> result = new ArrayList<>();

        // 2️⃣ 각 질문마다 답변 1개씩 가져와 DTO로 변환
        for (LectureQuestion q : questions) {
            LectureAnswer answer = lectureAnswerRepository
                    .findFirstByLectureQuestion_LectureQuestionId(q.getLectureQuestionId())
                    .orElse(null);

            LectureQuestionDTO.ReadQna dto = LectureQuestionDTO.ReadQna.builder()
                    .questionId(q.getLectureQuestionId())
                    .title(q.getTitle())
                    .content(q.getContent())
                    .isSolved(q.getIsSolved())
                    .questionCreatedAt(q.getCreatedAt())
                    .lectureId(q.getLecture().getLectureId())
                    .indexTitle(q.getLectureIndex().getIndexTitle())
                    .lectureIndexId(q.getLectureIndex().getLectureIndexId())
                    .answerContent(answer != null ? answer.getContent() : null)
                    .answerCreatedAt(answer != null ? answer.getCreatedAt() : null)
                    .build();

            result.add(dto);
        }

        return result;
    }






    public static class Write {
        private Long lectureQuestionId;
        private Long lectureId;
        private String title;
        private String content;
        private Long memberId;
        private String memberNickname;
        private LocalDateTime createdAt;
    }



}
