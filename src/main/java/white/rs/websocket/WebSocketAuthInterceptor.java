package white.rs.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import white.rs.common.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * WebSocket 认证拦截器
 * 在建立 WebSocket 连接前验证 JWT Token
 */
@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketAuthInterceptor.class);

    private static final String TOKEN_PARAM = "token";
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();

            // 从请求参数或请求头中获取 Token
            String token = getTokenFromRequest(httpRequest);

            if (!StringUtils.hasText(token)) {
                logger.warn("WebSocket 连接缺少 Token");
                return false;
            }

            // 验证 Token
            if (!jwtUtil.validateToken(token)) {
                logger.warn("WebSocket 连接 Token 无效");
                return false;
            }

            // 从 Token 中获取用户信息
            Long userId = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);

            if (userId == null || username == null) {
                logger.warn("WebSocket 连接无法从 Token 中获取用户信息");
                return false;
            }

            // 将用户信息存储到 attributes 中，供 WebSocketHandler 使用
            attributes.put("userId", userId);
            attributes.put("username", username);
            attributes.put("token", token);

            logger.info("WebSocket 连接认证成功，用户ID: {}, 用户名: {}", userId, username);
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 握手完成后的处理，这里不需要特殊处理
    }

    /**
     * 从请求中获取 Token
     * 优先从请求参数中获取，其次从请求头中获取
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 1. 从请求参数中获取
        String token = request.getParameter(TOKEN_PARAM);
        if (StringUtils.hasText(token)) {
            return token;
        }

        // 2. 从请求头中获取
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}

