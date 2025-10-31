package studyon.app.layer.domain.chatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture.repository.LectureRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {
    private final OpenAiChatModel chatModel;
    private final LectureRepository lectureRepository;

//    @Override
//    public String getAnswer(String question) {
//        return chatModel.call(question);
//    }

    @Override
    public String getAnswer(String question) {
        // 1️⃣ intent 분류
        String intent = detectIntent(question);
        log.info("🎯 [의도 분류 결과] {}", intent);

        // 2️⃣ intent별 분기
        switch (intent) {
            case "lecture":
                return handleLectureIntent(question);
            case "faq":
                return "FAQ 기능은 아직 준비 중입니다.";
            case "policy":
                return "정책 안내 기능은 아직 준비 중입니다.";
            default:
                return "죄송합니다. 강의, FAQ, 정책 관련 질문만 답변할 수 있습니다.";
        }
    }

    @Override
    public String detectIntent(String question) {
        // 1️⃣ Rule-based 1차 필터
        if (question.contains("강의") || question.contains("수강") || question.contains("추천")) return "lecture";
        if (question.contains("환불") || question.contains("결제") || question.contains("가격")) return "policy";
        if (question.contains("로그인") || question.contains("비밀번호") || question.contains("회원")) return "faq";

        // 2️⃣ GPT 기반 2차 분류
        String prompt = """
            너는 온라인 학습 플랫폼 'StudyOn'의 AI 챗봇이다.
            사용자의 질문이 어떤 의도(intent)에 해당하는지 분류해라.

            가능한 intent 종류:
            - lecture : 강의 추천, 강사 정보, 수강 관련
            - faq : 회원가입, 로그인, 비밀번호,고객센터 전화번호 등 계정 관련
            - policy : 결제, 환불, 쿠폰, 이용 기간 관련
            - none : 위에 해당하지 않음

            오직 intent 이름만 출력해라.
            사용자 질문: %s
        """.formatted(question);

        try {
            String result = chatModel.call(prompt).toLowerCase();
            if (result.contains("lecture")) return "lecture";
            if (result.contains("faq")) return "faq";
            if (result.contains("policy")) return "policy";
            return "none";
        } catch (Exception e) {
            return "none";
        }
    }

    private String handleLectureIntent(String question) {
        List<Lecture> lectures = lectureRepository.findAll();

        // GPT에 던질 강의 데이터 문자열화
        String lectureData = lectures.stream()
                .map(l -> String.format(
                        "강의명: %s | 가격: %.0f원 | 난이도: %s | 평점: %.2f점 | 학생 수: %d명 | 영상 수: %d개",
                        l.getTitle(),
                        l.getPrice().doubleValue(),  // Double이므로 %.0f로 처리
                        l.getDifficulty(),
                        l.getAverageRate(),
                        l.getTotalStudents() != null ? l.getTotalStudents() : 0,
                        l.getVideoCount() != null ? l.getVideoCount() : 0
                ))
                .collect(Collectors.joining("\n"));

        // GPT 프롬프트
        String prompt = """
            너는 'StudyOn' 사이트의 AI 강의 추천 챗봇이다.
            아래는 현재 판매 중인 강의 목록이다.

            %s

            위 목록 중에서 사용자의 질문과 가장 관련 있는 강의 하나를 추천해라.
            강의명과 이유를 간단히 말해라.
            사용자 질문: %s
        """.formatted(lectureData, question);

        return chatModel.call(prompt);
    }
}
