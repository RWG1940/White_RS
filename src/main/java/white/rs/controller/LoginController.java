package white.rs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import white.rs.common.response.WhiteResponse;
import white.rs.common.util.JwtUtil;
import white.rs.common.util.PasswordUtil;
import white.rs.domain.Users;
import white.rs.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/api/auth")
@Api(tags = "登录控制器")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsersService usersService;

    /**
     * 用户登录
     * POST /api/auth/login
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public WhiteResponse<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username == null || username.trim().isEmpty()) {
            return WhiteResponse.fail(400, "用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return WhiteResponse.fail(400, "密码不能为空");
        }

        try {
            // 使用Spring Security进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 认证成功，设置到SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 获取用户信息
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Users user = usersService.getByUsername(username);

            // 生成JWT Token
            String token = jwtUtil.generateToken(user.getId(), username);

            // 查询用户角色
            List<String> roleCodes = usersService.getRoleCodesByUserId(user.getId());

            // 更新登录信息
            String loginIp = getClientIp(request);
            usersService.updateLoginInfo(user.getId(), loginIp);

            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("username", username);
            result.put("userId", user.getId());
            result.put("email", user.getEmail());
            result.put("phone", user.getPhone());
            result.put("roles", roleCodes);

            return WhiteResponse.success("登录成功", result);

        } catch (BadCredentialsException e) {
            return WhiteResponse.fail(401, "用户名或密码错误");
        } catch (Exception e) {
            return WhiteResponse.fail(500, "登录失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     * GET /api/auth/current
     */
    @GetMapping("/current")
    @ApiOperation("获取当前用户信息")
    public WhiteResponse<Map<String, Object>> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return WhiteResponse.fail(401, "未登录");
            }

            String username = authentication.getName();
            Users user = usersService.getByUsername(username);
            if (user == null) {
                return WhiteResponse.fail(404, "用户不存在");
            }

            List<String> roleCodes = usersService.getRoleCodesByUserId(user.getId());

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

    /**
     * 用户登出
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public WhiteResponse<Void> logout() {
        SecurityContextHolder.clearContext();
        return WhiteResponse.success("登出成功", null);
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

    /**
     * 修改密码·
     *
     */
    @PostMapping("/change-password")
    @ApiOperation("修改密码")
    public WhiteResponse changePassword(
            @RequestBody Map<String, String> req
    ) {
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
        Users user = usersService.getOne(queryWrapper);
        // 验证旧密码是否正确
        if (!PasswordUtil.matches(oldPassword,user.getPasswordHash())) {
            return WhiteResponse.fail("旧密码错误");
        }
        // 修改密码
        user.setPasswordHash(PasswordUtil.encode(newPassword));

        return usersService
                .update(user, queryWrapper)
                ? WhiteResponse.success("密码修改成功")
                : WhiteResponse.fail("密码修改失败");

    }

}

