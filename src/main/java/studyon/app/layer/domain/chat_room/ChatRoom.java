package studyon.app.layer.domain.chat_room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
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
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;  // PK 딱 하나만

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 학생(고객) 기준
    private Member member; // ✅ 유저 정보 직접 참조

    @Builder
    public ChatRoom(String roomName, Member member) {
        this.roomName = roomName;
        this.member = member;
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
