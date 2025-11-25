package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import white.rs.domain.Users;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【users(后台用户表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:23:19
 * @Entity white.rs.domain.Users
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 根据用户名查询用户（包含角色信息）
     *
     * @param username 用户名
     * @return 用户信息
     */
    Users selectByUsernameWithRoles(@Param("username") String username);

    /**
     * 根据用户ID查询用户角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
