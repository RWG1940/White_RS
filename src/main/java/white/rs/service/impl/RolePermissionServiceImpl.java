package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.RolePermission;
import white.rs.service.RolePermissionService;
import white.rs.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【role_permission(角色-权限关联表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionService {

}
