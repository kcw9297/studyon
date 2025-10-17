package studyon.app.layer.domain.chat;// Chat.java - placeholder file

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.chat_room.ChatRoom;
import studyon.app.layer.domain.member.Member;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khs97 최초 작성
 */

/**
 * 강의 서비스 인터페이스
 * @version 1.0
 * @author khs97
 */


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Chat(ChatRoom chatRoom, String message, Member sender) {
        this.chatRoom = chatRoom;
        this.message = message;
        this.sender = sender;
    }
}