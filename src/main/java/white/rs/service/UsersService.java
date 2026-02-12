package white.rs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.DTO.UserWithRolesDTO;
import white.rs.domain.Users;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    
    /**
     * 分页查询用户及其角色
     *
     * @param current 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    IPage<UserWithRolesDTO> pageUsersWithRoles(Long current, Long size);
    
    /**
     * 模糊搜索用户及其角色
     *
     * @param column 搜索列
     * @param keyword 搜索关键词
     * @return 用户及角色信息DTO列表
     */
    List<UserWithRolesDTO> searchUsersWithRoles(String column, String keyword);
    
    /**
     * 保存用户及其角色并返回用户角色信息
     *
     * @param user 用户实体
     * @param roleIds 角色ID列表
     * @return 用户及角色信息DTO
     */
    UserWithRolesDTO saveUserWithRolesAndReturnDTO(Users user, List<Long> roleIds);
    
    /**
     * 更新用户及其角色并返回用户角色信息
     *
     * @param user 用户实体
     * @param roleIds 角色ID列表
     * @return 用户及角色信息DTO
     */
    UserWithRolesDTO updateUserWithRolesAndReturnDTO(Users user, List<Long> roleIds);
    
    /**
     * 删除用户及其相关的角色关系并返回响应
     *
     * @param userId 用户ID
     * @return WhiteResponse响应对象
     */
    WhiteResponse<Void> removeUserWithRolesAndReturnResponse(Long userId);
    
    /**
     * 批量删除用户及其相关的角色关系并返回响应
     *
     * @param userIds 用户ID列表
     * @return WhiteResponse响应对象
     */
    WhiteResponse<Void> removeUsersWithRolesAndReturnResponse(List<Long> userIds);
    
    /**
     * 保存用户及其角色并返回响应
     *
     * @param user 用户实体
     * @param roleIds 角色ID列表
     * @return WhiteResponse响应对象
     */
    WhiteResponse<UserWithRolesDTO> saveUserWithRolesAndReturnResponse(Users user, List<Long> roleIds);
    
    /**
     * 更新用户及其角色并返回响应
     *
     * @param user 用户实体
     * @param roleIds 角色ID列表
     * @return WhiteResponse响应对象
     */
    WhiteResponse<UserWithRolesDTO> updateUserWithRolesAndReturnResponse(Users user, List<Long> roleIds);

    WhiteResponse<Map<String, Object>> userLogin(String username, HttpServletRequest request);

    WhiteResponse<Map<String, Object>> getCurrentUser();

    WhiteResponse changePassword(Map<String, String> req);

    WhiteResponse register(Map<String, String> req);

    WhiteResponse<String> getUsernameById(Long id);
}