package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Permissions;


/**
 * @author Administrator
 * @description 针对表【permissions(权限表（权限点）)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Permissions
 */
@Mapper
public interface PermissionsMapper extends BaseMapper<Permissions> {


}
