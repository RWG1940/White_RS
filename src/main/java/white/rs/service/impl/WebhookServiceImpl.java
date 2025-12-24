package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Webhook;
import white.rs.service.WebhookService;
import white.rs.mapper.WebhookMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【webhook】的数据库操作Service实现
* @createDate 2025-12-23 17:04:18
*/
@Service
public class WebhookServiceImpl extends ServiceImpl<WebhookMapper, Webhook>
    implements WebhookService{

}




