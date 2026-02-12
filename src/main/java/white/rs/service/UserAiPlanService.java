package white.rs.service;

import white.rs.common.response.WhiteResponse;
import white.rs.domain.UserAiPlan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user_ai_plan】的数据库操作Service
* @createDate 2026-02-03 15:39:32
*/
public interface UserAiPlanService extends IService<UserAiPlan> {

    WhiteResponse pageWithInfo(Long current, Long size);
}
