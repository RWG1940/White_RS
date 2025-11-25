package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.OperationLogs;
import white.rs.service.OperationLogsService;
import white.rs.mapper.OperationLogsMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【operation_logs(操作日志表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class OperationLogsServiceImpl extends ServiceImpl<OperationLogsMapper, OperationLogs>
        implements OperationLogsService {

}
