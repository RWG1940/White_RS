package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.AiPlans;
import white.rs.service.AiPlansService;


@RestController
@RequestMapping("/ai-plans")
@Api(tags = "ai套餐订阅")
public class AiPlansController extends BaseController<AiPlans, AiPlansService> {

    protected final AiPlansService service;
    protected AiPlansController(AiPlansService service) {
        super(service);
        this.service = service;
    }
}