package studyon.app.layer.domain.member_chat_room;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.common.enums.ChatRole;
import studyon.app.layer.domain.chat_room.ChatRoom;
import studyon.app.layer.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MemberChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberChatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =  "ENUM('USER','ADMIN') DEFAULT 'USER'", nullable = false)
    private ChatRole chatRole = ChatRole.USER;

    @Builder
    public MemberChatRoom(ChatRole chatRole, ChatRoom chatRoom, Member member) {
        this.chatRole = chatRole != null ? chatRole : ChatRole.USER;
        this.chatRoom = chatRoom;
        this.member = member;
    }
}
