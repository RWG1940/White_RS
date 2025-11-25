package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import white.rs.domain.Users;
import white.rs.mapper.UsersMapper;
import white.rs.service.UsersService;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【users(后台用户表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:23:19
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Override
    public Users getByUsername(String username) {
        return baseMapper.selectByUsernameWithRoles(username);
    }

    @Override
    public List<String> getRoleCodesByUserId(Long userId) {
        return baseMapper.selectRoleCodesByUserId(userId);
    }

    @Override
    public void updateLoginInfo(Long userId, String loginIp) {
        Users user = new Users();
        user.setId(userId);
        user.setLastLoginAt(new Date());
        user.setLastLoginIp(loginIp);
        updateById(user);
    }
}
