package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import white.rs.common.util.PasswordUtil;
import white.rs.domain.Roles;
import white.rs.domain.UserRole;
import white.rs.domain.DTO.UserWithRolesDTO;
import white.rs.domain.Users;
import white.rs.mapper.RolesMapper;
import white.rs.mapper.UserRoleMapper;
import white.rs.mapper.UsersMapper;
import white.rs.service.UsersService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    public Users getByUsername(String username) {
        return usersMapper.selectByUsernameWithRoles(username);
    }

    @Override
    public List<String> getRoleCodesByUserId(Long userId) {
        return usersMapper.selectRoleCodesByUserId(userId);
    }

    @Override
    public void updateLoginInfo(Long userId, String loginIp) {
        Users user = new Users();
        user.setId(userId);
        user.setLastLoginAt(new Date());
        user.setLastLoginIp(loginIp);
        updateById(user);
    }

    // 重新添加新用户
    @Override
    public boolean save(Users entity) {
        // 添加初始加密的密码为1234
        entity.setPasswordHash(PasswordUtil.encode("1234"));
        return super.save(entity);
    }

    @Override
    public UserWithRolesDTO getUserWithRolesById(Long userId) {
        // 获取用户信息
        Users user = getById(userId);
        if (user == null) {
            return null;
        }

        // 获取用户角色列表
        List<Roles> roles = getRolesByUserId(userId);

        // 封装成DTO
        return new UserWithRolesDTO(user, roles);
    }

    @Override
    public List<UserWithRolesDTO> listUsersWithRoles() {
        // 获取所有用户
        List<Users> users = list();
        
        // 获取所有用户的ID
        List<Long> userIds = users.stream()
                .map(Users::getId)
                .collect(Collectors.toList());

        // 批量获取用户角色映射
        List<UserWithRolesDTO> userWithRolesList = new ArrayList<>();
        for (Users user : users) {
            List<Roles> roles = getRolesByUserId(user.getId());
            userWithRolesList.add(new UserWithRolesDTO(user, roles));
        }

        return userWithRolesList;
    }

    @Override
    public List<UserWithRolesDTO> listUsersWithRolesByIds(List<Long> userIds) {
        // 获取指定用户
        List<Users> users = listByIds(userIds);
        
        // 批量获取用户角色映射
        List<UserWithRolesDTO> userWithRolesList = new ArrayList<>();
        for (Users user : users) {
            List<Roles> roles = getRolesByUserId(user.getId());
            userWithRolesList.add(new UserWithRolesDTO(user, roles));
        }

        return userWithRolesList;
    }

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUserWithRoles(Long userId) {
        // 删除用户角色关系
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        userRoleMapper.delete(queryWrapper);

        // 删除用户
        return removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUsersWithRoles(List<Long> userIds) {
        // 删除用户角色关系
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        userRoleMapper.delete(queryWrapper);

        // 删除用户
        return removeByIds(userIds);
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
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