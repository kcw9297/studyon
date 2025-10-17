package studyon.app.layer.domain.chat_room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khs97 최초 작성
 */

/**
 * 채팅방 엔티티 클래스
 * @version 1.0
 * @author khs97
 */


@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;  // PK 딱 하나만

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public ChatRoom(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "chatRoomId=" + chatRoomId +
                ", roomName='" + roomName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
