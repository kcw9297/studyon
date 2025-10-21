package studyon.app.layer.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.chat.ChatDTO;
import studyon.app.layer.domain.chat.repository.ChatRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final OpenAiChatModel chatModel;
    private final ChatRepository chatRepository;

    @Override
    public String getAnswer(String question) {
        return chatModel.call(question);
    }

    @Override
    public List<ChatDTO.Read> getMessagesByRoomId(Long roomId) {
        return chatRepository.findByChatRoom_ChatRoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(chat -> ChatDTO.Read.builder()
                        .chatId(chat.getChatId())
                        .chatRoomId(chat.getChatRoom().getChatRoomId())
                        .senderId(chat.getSender() != null ? chat.getSender().getMemberId() : null)
                        .message(chat.getMessage())
                        .build())
                .collect(Collectors.toList());
    }


}
