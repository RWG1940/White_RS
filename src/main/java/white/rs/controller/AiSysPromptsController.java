package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.AiSysPrompts;
import white.rs.service.AiSysPromptsService;

@RestController
@RequestMapping("/ai-sys-prompts")
@Api(tags = "ai系统提示词管理")
public class AiSysPromptsController extends BaseController<AiSysPrompts, AiSysPromptsService> {

    protected final AiSysPromptsService service;
    protected AiSysPromptsController(AiSysPromptsService service) {
        super(service);
        this.service = service;
    }
}