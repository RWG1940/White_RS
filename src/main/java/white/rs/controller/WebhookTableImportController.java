package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.WebhookTableImport;
import white.rs.service.WebhookTableImportService;
import white.rs.common.response.WhiteResponse;

@RestController
@RequestMapping("/webhook-table-import")
@Api(tags = "webhook与批次表的关联管理")
public class WebhookTableImportController extends BaseController<WebhookTableImport, WebhookTableImportService> {

    @Autowired
    private WebhookTableImportService webhookTableImportService;
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookTableImportController.class);

    /**
     * 批量通知,根据该条数据的tableImportId去获取对应的webhook id,在后端实现批量通知功能
     */
    @GetMapping("/notice-group/{tableImportId}/{sku}")
    @ApiOperation("批量通知")
    public WhiteResponse noticeGroup(@PathVariable String tableImportId, @PathVariable String sku) {
        logger.info("接收到批量通知请求，tableImportId: {}", tableImportId);
        
        boolean result = webhookTableImportService.noticeGroup(tableImportId, sku);
        
        if (result) {
            logger.info("批量通知成功，tableImportId: {}", tableImportId);
            return WhiteResponse.success("已通知企微群聊更新");
        } else {
            logger.error("批量通知失败，tableImportId: {}", tableImportId);
            return WhiteResponse.success("暂未配置提醒功能");
        }
    }
}