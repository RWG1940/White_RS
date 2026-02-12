package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.Generaterecord;
import white.rs.service.GeneraterecordService;

@RestController
@RequestMapping("/ai-pic-record")
@Api(tags = "图片生成记录管理")
public class GenerateRecordController extends BaseController<Generaterecord, GeneraterecordService> {

    protected final GeneraterecordService service;
    protected GenerateRecordController(GeneraterecordService service) {
        super(service);
        this.service = service;
    }
}