package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Sessions;


/**
 * @author Administrator
 * @description 针对表【sessions(用户会话表（管理登录Token）)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Sessions
 */
@Mapper
public interface SessionsMapper extends BaseMapper<Sessions> {


}
