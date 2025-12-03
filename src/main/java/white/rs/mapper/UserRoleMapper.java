package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import white.rs.domain.UserRole;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【user_role(用户-角色关联表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.UserRole
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 批量插入用户角色关系
     *
     * @param userRoles 用户角色关系列表
     * @return 插入的记录数
     */
    int insertBatchSomeColumn(@Param("list") List<UserRole> userRoles);
}