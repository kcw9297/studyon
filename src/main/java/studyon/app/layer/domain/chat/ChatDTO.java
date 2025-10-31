package studyon.app.layer.domain.chat;


import lombok.*;

import java.util.function.LongFunction;

/**
 * 채팅 기본 정보 DTO
 * @version 1.0
 * @author khj00
 */


@NoArgsConstructor
public class ChatDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read {
        private Long chatId;
        private Long chatRoomId;
        private Long senderId;
        private String message;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Write {
        private Long chatRoomId;
        private Long senderId;
        private String message;
    }

}
