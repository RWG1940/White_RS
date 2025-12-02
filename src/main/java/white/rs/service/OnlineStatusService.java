package white.rs.service;

import java.util.Set;

/**
 * 在线状态服务接口
 */
public interface OnlineStatusService {

    /**
     * 设置用户在线
     *
     * @param userId 用户ID
     */
    void setUserOnline(Long userId);

    /**
     * 设置用户离线
     *
     * @param userId 用户ID
     */
    void setUserOffline(Long userId);

    /**
     * 检查用户是否在线
     *
     * @param userId 用户ID
     * @return 是否在线
     */
    boolean isUserOnline(Long userId);

    /**
     * 获取所有在线用户ID
     *
     * @return 在线用户ID集合
     */
    Set<Long> getOnlineUserIds();

    /**
     * 获取在线用户数量
     *
     * @return 在线用户数量
     */
    long getOnlineUserCount();
}

