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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LectureQuestionServiceImpl implements LectureQuestionService {

    private final LectureAnswerRepository lectureAnswerRepository;
    private final LectureQuestionRepository lectureQuestionRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;
    private final LectureIndexRepository lectureIndexRepository;

    // ✅ 모든 질문 조회
    @Override
    public List<LectureQuestionDTO.Read> readAllQuestions() {
        return lectureQuestionRepository.findAll()
                .stream()
                .map(DTOMapper::toReadDTO)
                .toList();
    }

    // ✅ 특정 질문 조회
    @Override
    public Optional<LectureQuestionDTO.Read> readQuestion(Long id) {
        return lectureQuestionRepository.findById(id).map(DTOMapper::toReadDTO);
    }

    // ✅ 질문 등록
    @Override
    public void register(LectureQuestionDTO.Write rq) {
        Lecture lecture = lectureRepository.findById(rq.getLectureId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));
        Member member = memberRepository.findById(rq.getMemberId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
        LectureIndex lectureIndex = lectureIndexRepository.findById(rq.getLectureIndexId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        LectureQuestion entity = LectureQuestion.builder()
                .title(rq.getTitle())
                .content(rq.getContent())
                .isSolved(false)
                .lecture(lecture)
                .member(member)
                .lectureIndex(lectureIndex)
                .build();

        lectureQuestionRepository.save(entity);
        log.info("✅ 질문 등록 완료: {}", entity.getLectureQuestionId());
    }

    // ✅ 질문 + 답변 조회 (커리큘럼 기준)
    @Override
    public List<LectureQuestionDTO.ReadQna> readQuestionAndAnswer(Long lectureId, Long lectureIndexId) {
        List<LectureQuestion> questions =
                lectureQuestionRepository.findByLecture_LectureIdAndLectureIndex_LectureIndexId(lectureId, lectureIndexId);

        List<LectureQuestionDTO.ReadQna> result = new ArrayList<>();

        for (LectureQuestion q : questions) {
            LectureAnswer answer = q.getLectureAnswer(); // 질문이 답변 참조
            LectureQuestionDTO.ReadQna dto = LectureQuestionDTO.ReadQna.builder()
                    .questionId(q.getLectureQuestionId())
                    .title(q.getTitle())
                    .content(q.getContent())
                    .isSolved(q.getIsSolved())
                    .questionCreatedAt(q.getCreatedAt())
                    .lectureId(q.getLecture().getLectureId())
                    .indexTitle(q.getLectureIndex() != null ? q.getLectureIndex().getIndexTitle() : "미지정 목차")
                    .lectureIndexId(q.getLectureIndex() != null ? q.getLectureIndex().getLectureIndexId() : null)
                    .answerContent(answer != null ? answer.getContent() : null)
                    .answerCreatedAt(answer != null ? answer.getCreatedAt() : null)
                    .build();
            result.add(dto);
        }
        return result;
    }

    // ✅ 강사별 QnA 목록 조회
    @Override
    public List<LectureQuestionDTO.ReadTeacherQnaDTO> getAllQnaList(Long teacherId) {
        List<LectureQuestion> list = lectureQuestionRepository.findAllWithAnswerByTeacherId(teacherId);

        return list.stream()
                .map(q -> LectureQuestionDTO.ReadTeacherQnaDTO.builder()
                        .lectureQuestionId(q.getLectureQuestionId())
                        .title(q.getTitle())
                        .content(q.getContent())
                        .studentName(q.getMember() != null ? q.getMember().getNickname() : "(알 수 없음)")
                        .lectureIndexId(q.getLectureIndex() != null ? q.getLectureIndex().getLectureIndexId() : null)
                        .indexTitle(q.getLectureIndex() != null ? q.getLectureIndex().getIndexTitle() : "미지정 목차")
                        .answered(q.getIsSolved())
                        .createdAt(q.getCreatedAt())
                        .answeredAt(q.getLectureAnswer() != null ? q.getLectureAnswer().getCreatedAt() : null)
                        .build())
                .toList();
    }

    // ✅ QnA 상세보기
    @Override
    public LectureQuestionDTO.TeacherQnaDetail readTeacherQnaDetail(Long questionId) {
        LectureQuestion q = lectureQuestionRepository.findByIdWithAnswer(questionId)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.QUESTION_NOT_FOUND));

        LectureAnswer answer = q.getLectureAnswer(); // fetch join으로 미리 로드됨

        log.info("📘 [QnA 조회] questionId={}, answer={}", q.getLectureQuestionId(),
                (answer != null ? answer.getContent() : "null"));

        return LectureQuestionDTO.TeacherQnaDetail.builder()
                .lectureQuestionId(q.getLectureQuestionId())
                .title(q.getTitle())
                .content(q.getContent())
                .studentName(q.getMember() != null ? q.getMember().getNickname() : "(알 수 없음)")
                .createdAt(q.getCreatedAt())
                .indexTitle(q.getLectureIndex() != null ? q.getLectureIndex().getIndexTitle() : "미지정 목차")
                .lectureId(q.getLecture().getLectureId())
                .lectureTitle(q.getLecture().getTitle())
                .teacherName(q.getLecture().getTeacher().getMember().getNickname())
                .answerContent(answer != null ? answer.getContent() : null)
                .answeredAt(answer != null ? answer.getCreatedAt() : null)
                .build();
    }



    // ✅ 질문 삭제
    @Override
    public void deleteQuestion(Long id) {
        LectureQuestion question = lectureQuestionRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(AppStatus.QUESTION_NOT_FOUND));

        // ✅ 1. 질문이 참조하고 있는 답변 null 처리
        question.updateQuestion(question.getTitle(), question.getContent(), false);
        question = lectureQuestionRepository.save(question);

        // ✅ 2. 질문 삭제 (연관관계 OnDelete에 따라 Answer는 SET_NULL or 유지됨)
        lectureQuestionRepository.delete(question);

        log.info("🗑️ 질문 삭제 완료 - questionId={}", id);
    }

    @Override
    public LectureQuestionDTO.Read createQuestion(LectureQuestionDTO.Write dto) {
        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.MEMBER_NOT_FOUND));
        LectureIndex lectureIndex = lectureIndexRepository.findById(dto.getLectureIndexId())
                .orElseThrow(() -> new BusinessLogicException(AppStatus.LECTURE_NOT_FOUND));

        LectureQuestion question = LectureQuestion.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .isSolved(false)
                .lecture(lecture)
                .member(member)
                .lectureIndex(lectureIndex)
                .build();

        LectureQuestion saved = lectureQuestionRepository.save(question);

        return DTOMapper.toReadDTO(saved);
    }
}
