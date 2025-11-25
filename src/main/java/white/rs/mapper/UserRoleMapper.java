package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.UserRole;


/**
 * @author Administrator
 * @description 针对表【user_role(用户-角色关联表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.UserRole
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {


}
