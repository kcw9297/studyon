package studyon.app.layer.domain.chatbot.service;

public interface ChatbotService {
    String getAnswer(String question);
    String detectIntent(String question);
}
