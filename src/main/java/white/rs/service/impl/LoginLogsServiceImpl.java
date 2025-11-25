package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.LoginLogs;
import white.rs.service.LoginLogsService;
import white.rs.mapper.LoginLogsMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【login_logs(登录日志表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class LoginLogsServiceImpl extends ServiceImpl<LoginLogsMapper, LoginLogs>
        implements LoginLogsService {

}
