package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.Webhook;
import white.rs.service.WebhookService;


@RestController
@RequestMapping("/webhook")
@Api(tags = "webhook管理、响应")
public class WebhookController extends BaseController<Webhook, WebhookService> {
    protected final WebhookService service;
    protected WebhookController(WebhookService service) {
        super(service);
        this.service = service;
    }
}
