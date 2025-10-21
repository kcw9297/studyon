package studyon.app.layer.domain.chat_room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studyon.app.layer.domain.chat_room.ChatRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // ✅ 사용자 ID 기반으로 방이 이미 존재하는지 확인 (선택적)
    ChatRoom findByRoomName(String roomName);
    Optional<ChatRoom> findByUserId(Long userId);
    List<ChatRoom> findAllByOrderByCreatedAtDesc();

}