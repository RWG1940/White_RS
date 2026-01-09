package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Feedback;
import white.rs.service.FeedbackService;
import white.rs.mapper.FeedbackMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【feedback】的数据库操作Service实现
* @createDate 2026-01-09 15:36:26
*/
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback>
    implements FeedbackService{

}




