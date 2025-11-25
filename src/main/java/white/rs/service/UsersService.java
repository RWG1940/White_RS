package white.rs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import white.rs.domain.Users;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【users(后台用户表)】的数据库操作Service
 * @createDate 2025-11-23 21:23:19
 */
public interface UsersService extends IService<Users> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Users getByUsername(String username);

    /**
     * 根据用户ID查询角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> getRoleCodesByUserId(Long userId);

    /**
     * 更新用户登录信息
     *
     * @param userId  用户ID
     * @param loginIp 登录IP
     */
    void updateLoginInfo(Long userId, String loginIp);
}
