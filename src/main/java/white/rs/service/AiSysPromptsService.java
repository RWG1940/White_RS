package white.rs.service;

import white.rs.common.response.WhiteResponse;
import white.rs.domain.AiSysPrompts;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【ai_sys_prompts】的数据库操作Service
* @createDate 2026-01-26 11:28:11
*/
public interface AiSysPromptsService extends IService<AiSysPrompts> {
    WhiteResponse pageBycategory(Long current, Long size, String category);

}
