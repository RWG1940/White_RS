package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.RolePermission;

/**
 * @author Administrator
 * @description 针对表【role_permission(角色-权限关联表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.RolePermission
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


}
