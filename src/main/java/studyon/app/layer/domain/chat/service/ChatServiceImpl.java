package studyon.app.layer.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final OpenAiChatModel chatModel;

    @Override
    public String getAnswer(String question) {
        return chatModel.call(question);
    }


}
