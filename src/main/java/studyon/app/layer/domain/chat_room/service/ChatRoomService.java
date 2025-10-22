package studyon.app.layer.domain.chat_room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studyon.app.layer.domain.chat_room.ChatRoom;
import studyon.app.layer.domain.chat_room.repository.ChatRoomRepository;
import studyon.app.layer.domain.member.Member;
import studyon.app.layer.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public Long createRoomForUser(Long userId) {
        // 1️⃣ 기존 방이 있으면 재사용
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByUserId(userId);
        if (existingRoom.isPresent()) {
            return existingRoom.get().getChatRoomId();
        }
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2️⃣ 없으면 새로 생성
        ChatRoom room = ChatRoom.builder()
                .roomName(member.getNickname() + userId)
                .userId(userId)
                .build();

        chatRoomRepository.save(room);
        return room.getChatRoomId();
    }

    public List<ChatRoom> getAllRooms() {
        return chatRoomRepository.findAllByOrderByCreatedAtDesc();
    }
}