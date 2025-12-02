package white.rs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import white.rs.service.OnlineStatusService;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 在线状态服务实现类
 * 使用 Redis 存储用户在线状态
 */
@Service
public class OnlineStatusServiceImpl implements OnlineStatusService {

    private static final Logger logger = LoggerFactory.getLogger(OnlineStatusServiceImpl.class);

    /**
     * Redis Key 前缀：在线用户集合
     */
    private static final String ONLINE_USERS_KEY = "online:users";

    /**
     * Redis Key 前缀：用户在线状态（带过期时间）
     */
    private static final String USER_ONLINE_KEY_PREFIX = "online:user:";

    /**
     * 用户在线状态过期时间（秒），默认30分钟
     * 如果30分钟内没有心跳，自动标记为离线
     */
    private static final long ONLINE_EXPIRE_SECONDS = 30 * 60;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setUserOnline(Long userId) {
        try {
            String userKey = USER_ONLINE_KEY_PREFIX + userId;
            // 设置用户在线状态，带过期时间
            redisTemplate.opsForValue().set(userKey, System.currentTimeMillis(), ONLINE_EXPIRE_SECONDS, TimeUnit.SECONDS);
            // 添加到在线用户集合
            redisTemplate.opsForSet().add(ONLINE_USERS_KEY, userId);
            logger.debug("用户 {} 已上线", userId);
        } catch (Exception e) {
            logger.error("设置用户 {} 在线状态失败", userId, e);
        }
    }

    @Override
    public void setUserOffline(Long userId) {
        try {
            String userKey = USER_ONLINE_KEY_PREFIX + userId;
            // 删除用户在线状态
            redisTemplate.delete(userKey);
            // 从在线用户集合中移除
            redisTemplate.opsForSet().remove(ONLINE_USERS_KEY, userId);
            logger.debug("用户 {} 已下线", userId);
        } catch (Exception e) {
            logger.error("设置用户 {} 离线状态失败", userId, e);
        }
    }

    @Override
    public boolean isUserOnline(Long userId) {
        try {
            String userKey = USER_ONLINE_KEY_PREFIX + userId;
            return Boolean.TRUE.equals(redisTemplate.hasKey(userKey));
        } catch (Exception e) {
            logger.error("检查用户 {} 在线状态失败", userId, e);
            return false;
        }
    }

    @Override
    public Set<Long> getOnlineUserIds() {
        try {
            Set<Object> userIds = redisTemplate.opsForSet().members(ONLINE_USERS_KEY);
            if (userIds == null || userIds.isEmpty()) {
                return java.util.Collections.emptySet();
            }
            // 过滤掉已过期的用户
            Set<Long> onlineUserIds = new java.util.HashSet<>();
            for (Object userId : userIds) {
                Long uid = convertToLong(userId);
                if (uid != null && isUserOnline(uid)) {
                    onlineUserIds.add(uid);
                }
            }
            return onlineUserIds;
        } catch (Exception e) {
            logger.error("获取在线用户列表失败", e);
            return java.util.Collections.emptySet();
        }
    }

    @Override
    public long getOnlineUserCount() {
        try {
            Set<Long> onlineUserIds = getOnlineUserIds();
            return onlineUserIds.size();
        } catch (Exception e) {
            logger.error("获取在线用户数量失败", e);
            return 0;
        }
    }

    /**
     * 刷新用户在线状态（延长过期时间）
     *
     * @param userId 用户ID
     */
    public void refreshUserOnline(Long userId) {
        try {
            String userKey = USER_ONLINE_KEY_PREFIX + userId;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(userKey))) {
                // 如果用户在线，刷新过期时间
                redisTemplate.expire(userKey, ONLINE_EXPIRE_SECONDS, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error("刷新用户 {} 在线状态失败", userId, e);
        }
    }

    /**
     * 将对象转换为 Long
     */
    private Long convertToLong(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

