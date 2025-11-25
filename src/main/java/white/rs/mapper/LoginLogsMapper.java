package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.LoginLogs;


/**
 * @author Administrator
 * @description 针对表【login_logs(登录日志表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.LoginLogs
 */
@Mapper
public interface LoginLogsMapper extends BaseMapper<LoginLogs> {


}
