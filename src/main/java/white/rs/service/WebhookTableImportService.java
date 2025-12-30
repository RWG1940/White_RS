package white.rs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.WebhookTableImport;

/**
 * @author Administrator
 * @description 针对表【webhook_table_import】的数据库操作Service
 * @createDate 2025-12-22 11:08:56
 */
public interface WebhookTableImportService extends IService<WebhookTableImport> {

    /**
     * 批量通知,根据该条数据的tableImportId去获取对应的webhook id,在后端实现批量通知功能
     */
    WhiteResponse noticeGroup(String tableImportId, String sku);
}