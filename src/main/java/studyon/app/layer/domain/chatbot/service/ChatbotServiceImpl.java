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
    private static final List<String> BLOCKED_KEYWORDS = List.of(
            // 기본 욕설
            "씨발", "시발", "쉬발", "씨8", "시8", "씨바", "씨빨", "씨뻘", "시빨", "씨파", "씨팍",
            "ㅅㅂ", "ㅆㅂ", "ㅆㅍ", "ㅅ발", "ㅆ발", "ㅅ바", "ㅅㅍ", "ㅅ ㅂ", "ㅆ ㅂ", "ㅅㅡㅂ",
            "개새", "개색", "개쉐", "개시발", "개씨발", "개같", "개자식", "개놈", "개년", "개소리",
            "개새끼", "개색기", "개색히", "개쉑", "개쌔끼", "개쌕", "개썅", "개시끼", "개자식",
            "ㅈㄴ", "ㅈㄹ", "ㅈ같", "ㅈ밥", "ㅈ망", "ㅈㅣ랄", "ㅈㄹ하네", "지랄", "지럴", "지ㄹ",
            "ㅈ같네", "ㅈ같은", "존나", "존내", "존니", "존싫", "좆", "좃", "좇", "좆같", "좆밥",
            "병신", "븅신", "ㅄ", "ㅂㅅ", "ㅂ신", "ㅂ ㅅ", "ㅂㄴ", "멍청", "미친", "미췬", "미쳤냐",
            "또라이", "또라2", "돌아이", "돌았냐", "도라이", "정신병", "ㅁㅊ", "ㅁ친",
            "발냄새", "고아", "거지같", "노답", "쓰레기", "병맛", "개노답",

            // 폭력/살인/위협 관련
            "죽여", "죽인다", "때려", "패버려", "찢어", "목졸라", "싸움", "싸워", "때리고", "폭력",
            "칼부림", "총쏴", "죽여버려", "죽고싶", "자살", "목매", "죽을래",

            // 성적 비하 / 성희롱 관련
            "ㅅㄲ", "ㅅㄱ", "섹스", "sex", "sexual", "fuck", "f*ck", "f@ck", "fu ck", "f u c k",
            "bitch", "b1tch", "btch", "b i t c h", "slut", "s l u t", "shit", "s h i t",
            "asshole", "a s s", "retard", "motherf", "dick", "cock", "pussy",

            // 인종/장애/차별 관련
            "retard", "spastic", "cripple", "autist", "idiot", "freak", "trash", "garbage", "loser",

            // 기타 변형 / 초성 축약 / 비하형
            "ㄲㅈ", "ㄲㅈㄹ", "ㅅㄲ", "ㅈㄹ", "ㅈㄴ", "ㅈㄹ하네", "ㅁㅊ", "ㅂㅅ", "ㄱㅅㄲ", "ㄱㅂㅅ",
            "시밞", "시뱕", "쉬빡", "시빡", "시뱔", "씹할", "씹팔", "씹할", "씹새", "씹년",
            "씹", "쌍놈", "쌍년", "등신", "멍청", "얼간이", "돌대가리", "저능아", "거지", "발냄새",
            "고아", "못생겼", "바보", "찐따", "똘추", "흉측", "장애", "정신병자"
    );

    @Override
    public String getAnswer(String question) {
        /* Service에서 GetAnswer를 요청하는 메서드를 호출시 1.의도 분류 2.의도에 적절한 프롬프트로 이동 */
        //비속어 필터링
        for (String word : BLOCKED_KEYWORDS) {
            if (question.contains(word)) {
                return "부적절한 표현이 포함되어 있어 답변할 수 없습니다.";
            }
        }

        // intent 분류
        String intent = detectIntent(question);
        log.info("🎯 [의도 분류 결과] {}", intent);

        // intent별 분기
        switch (intent) {
            case "lecture":
                return handleLectureIntent(question);
            case "faq":
                return handleFaqIntent(question);
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

    private String handleFaqIntent(String question) {
        String normalized = question.replaceAll("\\s+", "").toLowerCase();

        if (normalized.contains("로그인")) {
            return "로그인이 되지 않는 경우, 비밀번호를 확인하거나 '비밀번호 찾기' 기능을 이용해 주세요. 문제가 지속되면 고객센터로 문의해 주세요.";
        }
        if (normalized.contains("비밀번호") || normalized.contains("패스워드")) {
            return "비밀번호를 잊으셨다면 로그인 화면의 '비밀번호 재설정' 기능을 이용하시면 됩니다.";
        }
        if (normalized.contains("회원가입")) {
            return "StudyOn 회원가입은 이메일 인증을 통해 간단하게 진행됩니다. 가입 버튼을 눌러 절차를 따라주세요.";
        }
        if (normalized.contains("탈퇴")) {
            return "회원 탈퇴는 마이페이지 > 계정 설정 > 회원 탈퇴 메뉴에서 가능합니다.";
        }
        if (normalized.contains("이메일")) {
            return "이메일 인증이 오지 않을 경우 스팸함을 확인하시거나, 인증 메일 재전송을 시도해 보세요.";
        }
        if (normalized.contains("문의") || normalized.contains("고객센터") || normalized.contains("전화") || normalized.contains("번호") || normalized.contains("상담")) {
            return """
                📞 StudyOn 고객센터 안내
                
                • 운영시간: 평일 09:00 ~ 18:00 (주말 및 공휴일 휴무)
                • 전화번호: 1544-8282
                • 이메일: support@studyon.com

                빠른 상담을 원하시면 전화 문의를, 상세 문의는 이메일을 이용해 주세요.
                """;
        }
        if (normalized.contains("개인정보") || normalized.contains("보안") || normalized.contains("보호")) {
            return "StudyOn은 모든 비밀번호를 안전하게 암호화하여 저장하며, 개인정보는 제3자에게 제공되지 않습니다.";
        }
        if (normalized.contains("앱") || normalized.contains("모바일") || normalized.contains("휴대폰")) {
            return "모바일 환경에서도 StudyOn 웹사이트 접속 후 강의를 시청할 수 있습니다. 크롬 브라우저 사용을 권장드립니다.";
        }

        return "회원가입, 로그인, 비밀번호, 계정 관리, 고객센터 문의 등 사이트 이용 관련 도움을 드릴 수 있습니다. 개인정보는 제공되지 않습니다.";
    }


}
