package studyon.app.layer.domain.chatbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studyon.app.layer.domain.chatbot.service.ChatbotService;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotApiController {

    private final ChatbotService chatbotService;

//    @PostMapping
//    public Map<String, String> askChatbot(@RequestBody Map<String, String> request) {
//        String question = request.get("message");
//        log.info("📩 [사용자 질문] {}", question);
//
//        String answer = chatbotService.getAnswer(question);
//        log.info("🤖 [챗봇 응답] {}", answer);
//
//        return Map.of("answer", answer);
//    }

    @PostMapping
    public Map<String, String> askChatbot(@RequestBody Map<String, String> request) {
        String question = request.get("message");
        log.info("📩 [사용자 질문] {}", question);

        // ✅ 1️⃣ 의도 감지
        String intent = chatbotService.getAnswer(question);
        log.info("🎯 [의도 분류 결과] {}", intent);

        // ✅ 2️⃣ 임시로 intent를 answer처럼 반환 (테스트용)
        return Map.of("answer", intent);
    }
}
