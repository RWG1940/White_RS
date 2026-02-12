package white.rs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.UserAiPlan;
import white.rs.service.UserAiPlanService;


@RestController
@RequestMapping("/user-plan")
@Api(tags = "用户订阅")
public class UserAiPlanController extends BaseController<UserAiPlan, UserAiPlanService> {

    protected final UserAiPlanService service;
    protected UserAiPlanController(UserAiPlanService service) {
        super(service);
        this.service = service;
    }

    @Override
    public WhiteResponse page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size
    ) {
        return service.pageWithInfo(current, size);
    }
}