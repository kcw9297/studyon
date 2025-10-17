package studyon.app.layer.domain.member_chat_room;


import lombok.*;
import studyon.app.common.enums.ChatRole;
import studyon.app.layer.domain.chat_room.ChatRoom;

import java.time.LocalDateTime;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 멤버-채팅방 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberChatRoomDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Read {
        private Long memberChatRoomId;
        private Long chatRoomId;
        private String roomName;
        private LocalDateTime createdAt;
        private ChatRole chatRole;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Write {
        private Long chatRoomId;
        private String roomName;
    }
}
