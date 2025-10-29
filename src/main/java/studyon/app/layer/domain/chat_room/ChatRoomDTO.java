package studyon.app.layer.domain.chat_room;

import lombok.*;

import java.time.LocalDateTime;


/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 채팅방 DTO
 * @version 1.0
 * @author khj00
 */

@NoArgsConstructor
public class ChatRoomDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {
        private Long chatRoomId;
        private String roomName;
        private LocalDateTime createdAt;
        private Long memberId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Write {
        private String roomName;
        private Long memberId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Edit {
        private Long chatRoomId;
        private String roomName;
    }
}
