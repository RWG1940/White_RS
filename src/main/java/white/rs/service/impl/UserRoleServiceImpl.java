package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.UserRole;
import white.rs.service.UserRoleService;
import white.rs.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【user_role(用户-角色关联表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

}
