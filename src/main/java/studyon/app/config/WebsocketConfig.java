package studyon.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import studyon.app.infra.websocket.ChatHandshakeInterceptor;
import studyon.app.infra.websocket.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat") // ws/chat/ 웹소켓 요청을 chatWebSocketHandler 객체가 처리
                .addInterceptors(new ChatHandshakeInterceptor())
                .setAllowedOriginPatterns("*");
    }


}
