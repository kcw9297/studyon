package studyon.app.layer.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.chat.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByChatRoom_ChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);
}
