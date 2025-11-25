package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Roles;


/**
 * @author Administrator
 * @description 针对表【roles(角色表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Roles
 */
@Mapper
public interface RolesMapper extends BaseMapper<Roles> {


}
