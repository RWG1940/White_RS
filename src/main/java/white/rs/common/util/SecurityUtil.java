package white.rs.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import white.rs.service.UsersService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全工具类，用于获取当前用户信息和角色
 */
public class SecurityUtil {
    
    private final UsersService usersService;
    
    public SecurityUtil(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * 获取当前登录用户的ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // 从JWT token中解析用户ID，这里需要根据实际实现调整
            // 在当前系统中，我们通过用户名查找用户ID
            String username = userDetails.getUsername();
            // 由于不能直接访问UsersService，需要通过其他方式实现
            return null; // 临时返回null，后续通过其他方式实现
        }
        return null;
    }
    
    /**
     * 获取当前登录用户的用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }
    
    /**
     * 获取当前用户的角色编码列表
     */
    public static List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .map(authority -> {
                        String role = authority.getAuthority();
                        // 移除 ROLE_ 前缀
                        if (role.startsWith("ROLE_")) {
                            return role.substring(5);
                        }
                        return role;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    
    /**
     * 检查当前用户是否拥有指定角色
     */
    public static boolean hasRole(String roleCode) {
        List<String> roles = getCurrentUserRoles();
        return roles != null && roles.contains(roleCode);
    }
}