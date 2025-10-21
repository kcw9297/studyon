package studyon.app.layer.domain.chat.service;

import studyon.app.layer.domain.chat.ChatDTO;

import java.util.List;

public interface ChatService {

    String getAnswer(String question);
    List<ChatDTO.Read> getMessagesByRoomId(Long roomId);
}
