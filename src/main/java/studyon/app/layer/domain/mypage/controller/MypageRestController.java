package studyon.app.layer.domain.mypage.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studyon.app.layer.base.utils.SessionUtils;
import studyon.app.infra.cache.manager.CacheManager;
import studyon.app.layer.domain.chat_room.service.ChatRoomService;
import studyon.app.layer.domain.member.MemberProfile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageRestController {

    private final ChatRoomService chatRoomService;
    private final CacheManager cacheManager;

    @GetMapping("/account")
    public ResponseEntity<MemberProfile> getAccountProfile(HttpServletRequest request) {
        Long memberId = SessionUtils.getMemberId(request);
        if (memberId == null) {
            log.warn("⚠️ 비로그인 사용자의 마이페이지 프로필 요청");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MemberProfile profile = cacheManager.getProfile(memberId, MemberProfile.class);
        log.info("✅ 마이페이지 프로필 조회: memberId={}, role={}", memberId, profile.getRole());
        return ResponseEntity.ok(profile);
    }
}