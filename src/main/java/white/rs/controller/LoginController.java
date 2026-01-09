package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import white.rs.common.response.WhiteResponse;
import white.rs.service.UsersService;

import javax.servlet.http.HttpServletRequest;
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
            return usersService.userLogin(username, request);
        } catch (BadCredentialsException e) {
            return WhiteResponse.fail(401, "用户名或密码错误");
        } catch (Exception e) {
            return WhiteResponse.fail(401, "登录失败，用户名或密码错误: " + e.getMessage());
        }

    }

    /**
     * 获取当前用户信息
     * GET /api/auth/current
     */
    @GetMapping("/current")
    @ApiOperation("获取当前用户信息")
    public WhiteResponse<Map<String, Object>> getCurrentUser() {
        return usersService.getCurrentUser();
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
     * 修改密码·
     */
    @PostMapping("/change-password")
    @ApiOperation("修改密码")
    public WhiteResponse changePassword(
            @RequestBody Map<String, String> req
    ) {
        return usersService.changePassword(req);
    }

}

