package white.rs.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * 用于生成和验证加密密码
 */
@Component
public class PasswordUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 加密明文密码
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码是否匹配
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 生成加密密码（用于测试或初始化数据）
     * 可以在main方法中调用此方法生成加密密码
     */
    public static void main(String[] args) {

        String rawPassword = "1234";
        String encodedPassword = encode(rawPassword);
        System.out.println("明文密码: " + rawPassword);
        System.out.println("加密密码: " + encodedPassword);
    }
}

