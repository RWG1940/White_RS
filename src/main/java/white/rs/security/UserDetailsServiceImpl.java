package white.rs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import white.rs.domain.Users;
import white.rs.service.UsersService;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security UserDetailsService实现
 * 用于加载用户信息和权限
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        Users user = usersService.getByUsername(username);

        if (user == null) {
            logger.warn("用户不存在: " + username);
        }

        // 检查账号状态：0禁用 1正常 2锁定
        if (user.getStatus() == null || user.getStatus() == 0) {
            logger.warn("账号已被禁用");
        }
        if (user.getStatus() == 2) {
            logger.warn("账号已被锁定");
        }

        // 查询用户角色编码
        List<String> roleCodes = usersService.getRoleCodesByUserId(user.getId());
        // 构建权限列表（使用ROLE_前缀）
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String roleCode : roleCodes) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));
        }

        // 构建UserDetails对象
        return User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(user.getStatus() == 2)
                .credentialsExpired(false)
                .disabled(user.getStatus() == 0)
                .build();
    }
}

