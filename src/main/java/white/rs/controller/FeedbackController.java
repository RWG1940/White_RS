package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.Feedback;
import white.rs.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
@Api(tags = "guest与批次表的关联管理")
public class FeedbackController extends BaseController<Feedback, FeedbackService> {

}