package white.rs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import white.rs.websocket.OnlineStatusWebSocketHandler;
import white.rs.websocket.WebSocketAuthInterceptor;

/**
 * WebSocket 配置类
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private OnlineStatusWebSocketHandler onlineStatusWebSocketHandler;

    @Autowired
    private WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 WebSocket 处理器
        // 路径：/ws/online
        // 允许跨域
        registry.addHandler(onlineStatusWebSocketHandler, "/ws/online")
                .addInterceptors(webSocketAuthInterceptor)
                .setAllowedOrigins("*");
    }
}

