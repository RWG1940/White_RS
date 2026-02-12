package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import white.rs.common.response.WhiteResponse;
import white.rs.common.util.JwtUtil;
import white.rs.common.util.PasswordUtil;
import white.rs.domain.Roles;
import white.rs.domain.UserRole;
import white.rs.domain.DTO.UserWithRolesDTO;
import white.rs.domain.Users;
import white.rs.mapper.RolesMapper;
import white.rs.mapper.UserRoleMapper;
import white.rs.mapper.UsersMapper;
import white.rs.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【users(后台用户表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:23:19
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolesMapper rolesMapper;


    @Autowired
    private JwtUtil jwtUtil;

    /*
     * 根据用户名查询用户信息
     */
    @Override
    public Users getByUsername(String username) {
        return usersMapper.selectByUsernameWithRoles(username);
    }

    /*
     * 根据用户ID获取角色列表
     */
    @Override
    public List<String> getRoleCodesByUserId(Long userId) {
        return usersMapper.selectRoleCodesByUserId(userId);
    }

    /*
     * 更新用户登录信息
     */
    @Override
    public void updateLoginInfo(Long userId, String loginIp) {
        Users user = new Users();
        user.setId(userId);
        user.setLastLoginAt(new Date());
        user.setLastLoginIp(loginIp);
        updateById(user);
    }

    /*
     * 保存用户
     */
    @Override
    public boolean save(Users entity) {
        // 添加初始加密的密码为1234
        entity.setPasswordHash(PasswordUtil.encode("1234"));
        return super.save(entity);
    }

    /*
     * 获取用户信息
     */
    @Override
    public UserWithRolesDTO getUserWithRolesById(Long userId) {
        Users user = getById(userId);
        if (user == null) {
            return null;
        }
        List<Roles> roles = getRolesByUserId(userId);
        return new UserWithRolesDTO(user, roles);
    }

    /*
     * 批量获取用户信息
     */
    @Override
    public List<UserWithRolesDTO> listUsersWithRoles() {
        List<Users> users = list();
        List<UserWithRolesDTO> userWithRolesList = new ArrayList<>();
        for (Users user : users) {
            List<Roles> roles = getRolesByUserId(user.getId());
            userWithRolesList.add(new UserWithRolesDTO(user, roles));
        }

        return userWithRolesList;
    }

    /*
     * 批量获取用户信息
     */
    @Override
    public List<UserWithRolesDTO> listUsersWithRolesByIds(List<Long> userIds) {
        List<Users> users = listByIds(userIds);
        List<UserWithRolesDTO> userWithRolesList = new ArrayList<>();
        for (Users user : users) {
            List<Roles> roles = getRolesByUserId(user.getId());
            userWithRolesList.add(new UserWithRolesDTO(user, roles));
        }
        return userWithRolesList;
    }

    /*
     * 保存用户和角色关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserWithRoles(Users user, List<Long> roleIds) {
        // 保存用户
        boolean saved = save(user);
        if (!saved) {
            return false;
        }
        // 保存用户角色关系
        if (roleIds != null && !roleIds.isEmpty()) {
            Long userId = user.getId();
            List<UserRole> userRoles = new ArrayList<>();
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setAssignedAt(new Date());
                // TODO: 设置分配者ID，暂时设置为null
                userRole.setAssignedBy(null);
                userRoles.add(userRole);
            }
            return userRoleMapper.insertBatchSomeColumn(userRoles) > 0;
        }
        return true;
    }

    /*
     * 更新用户和角色关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserWithRoles(Users user, List<Long> roleIds) {
        // 更新用户
        boolean updated = updateById(user);
        if (!updated) {
            return false;
        }

        // 删除原有的用户角色关系
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        userRoleMapper.delete(queryWrapper);

        // 插入新的用户角色关系
        if (roleIds != null && !roleIds.isEmpty()) {
            Long userId = user.getId();
            List<UserRole> userRoles = new ArrayList<>();

            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setAssignedAt(new Date());
                // TODO: 设置分配者ID，暂时设置为null
                userRole.setAssignedBy(null);
                userRoles.add(userRole);
            }

            return userRoleMapper.insertBatchSomeColumn(userRoles) > 0;
        }

        return true;
    }

    /*
     * 删除用户和角色关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUserWithRoles(Long userId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        userRoleMapper.delete(queryWrapper);
        return removeById(userId);
    }

    /*
     * 批量删除用户和角色关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUsersWithRoles(List<Long> userIds) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        userRoleMapper.delete(queryWrapper);
        return removeByIds(userIds);
    }

    /*
     * 分页查询用户和角色关系
     */
    @Override
    public IPage<UserWithRolesDTO> pageUsersWithRoles(Long current, Long size) {
        // 创建分页对象
        IPage<Users> userPage = new Page<>(current, size);
        // 查询用户分页数据
        IPage<Users> pageResult = page(userPage);
        // 转换为UserWithRolesDTO分页数据
        IPage<UserWithRolesDTO> dtoPage = new Page<>(current, size, pageResult.getTotal());
        // 获取用户ID列表
        List<Long> userIds = pageResult.getRecords().stream()
                .map(Users::getId)
                .collect(Collectors.toList());
        // 批量查询用户及其角色
        List<UserWithRolesDTO> userWithRolesList = listUsersWithRolesByIds(userIds);
        dtoPage.setRecords(userWithRolesList);
        return dtoPage;
    }

    /*
     * 搜索用户和角色关系
     */
    @Override
    public List<UserWithRolesDTO> searchUsersWithRoles(String column, String keyword) {
        // 构建查询条件
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(column, keyword);
        // 查询用户列表
        List<Users> users = list(queryWrapper);
        // 获取用户ID列表
        List<Long> userIds = users.stream()
                .map(Users::getId)
                .collect(Collectors.toList());
        // 批量查询用户及其角色
        return listUsersWithRolesByIds(userIds);
    }

    /*
     * 保存用户和角色关系并返回DTO
     */
    @Override
    public UserWithRolesDTO saveUserWithRolesAndReturnDTO(Users user, List<Long> roleIds) {
        boolean saved = saveUserWithRoles(user, roleIds);
        if (saved) {
            return getUserWithRolesById(user.getId());
        }
        return null;
    }

    /*
     * 更新用户和角色关系并返回DTO
     */
    @Override
    public UserWithRolesDTO updateUserWithRolesAndReturnDTO(Users user, List<Long> roleIds) {
        boolean updated = updateUserWithRoles(user, roleIds);
        if (updated) {
            return getUserWithRolesById(user.getId());
        }
        return null;
    }

    /*
     * 删除用户和角色关系并返回响应
     */
    @Override
    public WhiteResponse<Void> removeUserWithRolesAndReturnResponse(Long userId) {
        boolean removed = removeUserWithRoles(userId);
        if (removed) {
            return WhiteResponse.success();
        }
        return WhiteResponse.fail("删除失败");
    }

    /*
     * 批量删除用户和角色关系并返回响应
     */
    @Override
    public WhiteResponse<Void> removeUsersWithRolesAndReturnResponse(List<Long> userIds) {
        boolean removed = removeUsersWithRoles(userIds);
        if (removed) {
            return WhiteResponse.success();
        }
        return WhiteResponse.fail("删除失败");
    }

    /*
     * 保存用户和角色关系并返回响应
     */
    @Override
    public WhiteResponse<UserWithRolesDTO> saveUserWithRolesAndReturnResponse(Users user, List<Long> roleIds) {
        UserWithRolesDTO userWithRoles = saveUserWithRolesAndReturnDTO(user, roleIds);
        if (userWithRoles != null) {
            return WhiteResponse.success(userWithRoles);
        }
        return WhiteResponse.fail("保存失败");
    }

    /*
     * 更新用户和角色关系并返回响应
     */
    @Override
    public WhiteResponse<UserWithRolesDTO> updateUserWithRolesAndReturnResponse(Users user, List<Long> roleIds) {
        UserWithRolesDTO userWithRoles = updateUserWithRolesAndReturnDTO(user, roleIds);
        if (userWithRoles != null) {
            return WhiteResponse.success(userWithRoles);
        }
        return WhiteResponse.fail("更新失败");
    }

    @Override
    public WhiteResponse<Map<String, Object>> userLogin(String username, HttpServletRequest request) {
            Users user = getByUsername(username);
            // 生成JWT Token
            String token = jwtUtil.generateToken(user.getId(), username);

            // 查询用户角色
            List<String> roleCodes = getRoleCodesByUserId(user.getId());

            // 更新登录信息
            String loginIp = getClientIp(request);
            updateLoginInfo(user.getId(), loginIp);

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("username", username);
            result.put("userId", user.getId());
            result.put("email", user.getEmail());
            result.put("phone", user.getPhone());
            result.put("roles", roleCodes);
            return WhiteResponse.success("登录成功", result);
    }

    @Override
    public WhiteResponse<Map<String, Object>> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return WhiteResponse.fail(401, "未登录");
            }

            String username = authentication.getName();
            Users user = getByUsername(username);
            if (user == null) {
                return WhiteResponse.fail(404, "用户不存在");
            }

            List<String> roleCodes = getRoleCodesByUserId(user.getId());

            Map<String, Object> result = new HashMap<>();
            result.put("userId", user.getId());
            result.put("username", user.getUsername());
            result.put("email", user.getEmail());
            result.put("phone", user.getPhone());
            result.put("status", user.getStatus());
            result.put("roles", roleCodes);
            result.put("avatarUrl", user.getAvatarUrl());

            return WhiteResponse.success(result);
        } catch (Exception e) {
            return WhiteResponse.fail(500, "获取用户信息失败: " + e.getMessage());
        }
    }

    @Override
    public WhiteResponse changePassword(Map<String, String> req) {
        String id = req.get("id");
        String oldPassword = req.get("oldPassword");
        String newPassword = req.get("newPassword");
        String confirmPassword = req.get("confirmPassword");
        // 验证新密码和确认密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            return WhiteResponse.fail("两次密码不一致");
        }
        // 构建QueryWrapper
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Users user = super.getOne(queryWrapper);
        // 验证旧密码是否正确
        if (!PasswordUtil.matches(oldPassword, user.getPasswordHash())) {
            return WhiteResponse.fail("旧密码错误");
        }
        // 修改密码
        user.setPasswordHash(PasswordUtil.encode(newPassword));

        return super
                .update(user, queryWrapper)
                ? WhiteResponse.success("密码修改成功")
                : WhiteResponse.fail("密码修改失败");


    }

    @Override
    public WhiteResponse register(Map<String, String> req)    {
        String username = req.get("username");
        // 判断用户名是否已存在
        if (getByUsername(username) != null) {
            return WhiteResponse.fail("用户名已存在");
        }
        String password = req.get("password");
        String email = req.get("email");
        String phone = req.get("phone");
        Users user = new Users();
        user.setUsername(username);
        user.setPasswordHash(PasswordUtil.encode(password));
        user.setEmail(email);
        user.setPhone(phone);
        return super
                .save(user)
                ? WhiteResponse.success("注册成功")
                : WhiteResponse.fail("注册失败");
    }

    @Override
    public WhiteResponse<String> getUsernameById(Long id) {
        return WhiteResponse.success(super.getById(id).getUsername());
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多个IP的情况，取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /*
     * 获取用户角色列表
     */
    private List<Roles> getRolesByUserId(Long userId) {
        // 查询用户角色关系
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        // 获取角色ID列表
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        // 如果没有角色，直接返回空列表
        if (roleIds.isEmpty()) {
            return new ArrayList<>();
        }
        // 查询角色信息
        return rolesMapper.selectBatchIds(roleIds);
    }
}