package white.rs.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import white.rs.service.OnlineStatusService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线状态 WebSocket 处理器
 * 处理用户连接、断开连接，并更新在线状态
 */
@Component
public class OnlineStatusWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(OnlineStatusWebSocketHandler.class);

    /**
     * 存储用户 WebSocket 会话
     * Key: 用户ID, Value: WebSocketSession
     */
    private final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Autowired
    private OnlineStatusService onlineStatusService;

    /**
     * 连接建立后调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            // 存储会话
            userSessions.put(userId, session);
            // 设置用户在线
            onlineStatusService.setUserOnline(userId);
            logger.info("用户 {} 建立 WebSocket 连接，当前在线用户数: {}", userId, userSessions.size());

            // 发送连接成功消息
            sendMessage(session, "{\"type\":\"connected\",\"message\":\"连接成功\"}");
        } else {
            logger.warn("WebSocket 连接建立失败，无法获取用户ID");
            session.close(CloseStatus.BAD_DATA);
        }
    }

    /**
     * 接收消息时调用
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            // 刷新用户在线状态（心跳机制）
            if (onlineStatusService instanceof white.rs.service.impl.OnlineStatusServiceImpl) {
                ((white.rs.service.impl.OnlineStatusServiceImpl) onlineStatusService).refreshUserOnline(userId);
            } else {
                onlineStatusService.setUserOnline(userId);
            }
            logger.debug("收到用户 {} 的心跳消息", userId);

            // 可以在这里处理其他业务消息
            String payload = message.getPayload();
            // 响应心跳
            if ("ping".equals(payload) || "heartbeat".equals(payload)) {
                sendMessage(session, "{\"type\":\"pong\",\"message\":\"pong\"}");
            }
        }
    }

    /**
     * 连接关闭后调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            // 移除会话
            userSessions.remove(userId);
            // 设置用户离线
            onlineStatusService.setUserOffline(userId);
            logger.info("用户 {} 关闭 WebSocket 连接，当前在线用户数: {}", userId, userSessions.size());
        }
    }

    /**
     * 传输错误时调用
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            logger.error("用户 {} WebSocket 传输错误", userId, exception);
            // 移除会话并设置离线
            userSessions.remove(userId);
            onlineStatusService.setUserOffline(userId);
        }
    }

    /**
     * 从 Session 中获取用户ID
     */
    private Long getUserIdFromSession(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        Object userId = attributes.get("userId");
        if (userId instanceof Long) {
            return (Long) userId;
        } else if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return null;
    }

    /**
     * 发送消息给指定会话
     */
    private void sendMessage(WebSocketSession session, String message) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            logger.error("发送 WebSocket 消息失败", e);
        }
    }

    /**
     * 获取指定用户的 WebSocket 会话
     */
    public WebSocketSession getUserSession(Long userId) {
        return userSessions.get(userId);
    }

    /**
     * 获取当前在线会话数量
     */
    public int getSessionCount() {
        return userSessions.size();
    }
}

