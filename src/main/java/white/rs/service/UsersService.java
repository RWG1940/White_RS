package white.rs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import white.rs.domain.DTO.UserWithRolesDTO;
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

    /**
     * 根据用户ID查询用户及其角色信息
     *
     * @param userId 用户ID
     * @return 用户及角色信息DTO
     */
    UserWithRolesDTO getUserWithRolesById(Long userId);

    /**
     * 查询所有用户及其角色信息列表
     *
     * @return 用户及角色信息DTO列表
     */
    List<UserWithRolesDTO> listUsersWithRoles();

    /**
     * 根据用户ID列表查询用户及其角色信息列表
     *
     * @param userIds 用户ID列表
     * @return 用户及角色信息DTO列表
     */
    List<UserWithRolesDTO> listUsersWithRolesByIds(List<Long> userIds);

    /**
     * 保存用户及其角色关系
     *
     * @param user 用户实体
     * @param roleIds 角色ID列表
     * @return 是否保存成功
     */
    boolean saveUserWithRoles(Users user, List<Long> roleIds);

    /**
     * 更新用户及其角色关系
     *
     * @param user 用户实体
     * @param roleIds 角色ID列表
     * @return 是否更新成功
     */
    boolean updateUserWithRoles(Users user, List<Long> roleIds);

    /**
     * 删除用户及其相关的角色关系
     *
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean removeUserWithRoles(Long userId);

    /**
     * 批量删除用户及其相关的角色关系
     *
     * @param userIds 用户ID列表
     * @return 是否删除成功
     */
    boolean removeUsersWithRoles(List<Long> userIds);
}