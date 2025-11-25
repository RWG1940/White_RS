package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Sessions;
import white.rs.service.SessionsService;
import white.rs.mapper.SessionsMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【sessions(用户会话表（管理登录Token）)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class SessionsServiceImpl extends ServiceImpl<SessionsMapper, Sessions>
        implements SessionsService {

}
