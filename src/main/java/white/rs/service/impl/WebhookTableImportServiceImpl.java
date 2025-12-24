package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import white.rs.domain.Webhook;
import white.rs.domain.WebhookTableImport;
import white.rs.service.WebhookService;
import white.rs.service.WebhookTableImportService;
import white.rs.mapper.WebhookTableImportMapper;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【webhook_table_import】的数据库操作Service实现
 * @createDate 2025-12-22 11:08:56
 */
@Service
public class WebhookTableImportServiceImpl extends ServiceImpl<WebhookTableImportMapper, WebhookTableImport>
    implements WebhookTableImportService{

    @Autowired
    private WebhookService webhookService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookTableImportServiceImpl.class);
    
    @Override
    public boolean noticeGroup(String tableImportId,String sku) {
        logger.info("开始执行批量通知，tableImportId: {}", tableImportId);
        
        try {
            // 根据tableImportId查询关联的webhook信息
            QueryWrapper<WebhookTableImport> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("import_id", tableImportId);
            List<WebhookTableImport> webhookTableImports = this.list(queryWrapper);
            
            logger.info("查询到关联的webhookTableImport记录数量: {}", webhookTableImports.size());

            if (webhookTableImports.isEmpty()) {
                logger.warn("未找到与tableImportId {} 关联的webhook", tableImportId);
                return false;
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            int successCount = 0;
            int failCount = 0;
            
            // 遍历所有关联的webhook并发送通知
            for (WebhookTableImport webhookTableImport : webhookTableImports) {
                Integer webhookId = webhookTableImport.getWebhookId();
                logger.info("处理webhookId: {}, 关联的importId: {}", webhookId, tableImportId);
                
                // 根据webhookId获取webhook详情
                Webhook webhook = webhookService.getById(webhookId);
                
                if (webhook == null) {
                    logger.warn("Webhook不存在，webhookId: {}", webhookId);
                    failCount++;
                    continue;
                }
                
                logger.debug("Webhook详情 - ID: {}, URL: {}, Status: {}, Name: {}", 
                    webhook.getId(), webhook.getUrl(), webhook.getStatus(), webhook.getName());
                
                if (webhook.getStatus() != null && webhook.getStatus() == 1) { // 检查webhook是否启用
                    String webhookUrl = webhook.getUrl();
                    
                    if (webhookUrl == null || webhookUrl.trim().isEmpty()) {
                        logger.warn("Webhook URL为空，webhookId: {}", webhookId);
                        failCount++;
                        continue;
                    }

                    // 准备企业微信 Webhook 消息数据
                    String payload =
                            "{"
                                    + "\"msgtype\":\"markdown\","
                                    + "\"markdown\":{"
                                    +     "\"content\":\"> **<font color=\\\"info\\\">数据更新通知</font>**\\n"
                                    +     "> 批次ID <font color=\\\"comment\\\">" + tableImportId + "</font>\\n"
                                    +     "> 货号 <font color=\\\"comment\\\">" + sku + "</font>\\n"
                                    +     "> <@all>\""
                                    + "}"
                                    + "}";
                    logger.debug("发送请求到webhook: {}, payload: {}", webhookUrl, payload);

                    HttpEntity<String> request = new HttpEntity<>(payload, headers);
                    
                    try {
                        // 发送POST请求到webhook
                        String response = restTemplate.postForObject(webhookUrl, request, String.class);
                        logger.info("成功发送webhook通知到: {}, 响应: {}", webhookUrl, response);
                        
                        // 更新webhook的发送次数
                        Integer currentSendCount = webhook.getSendcount();
                        if (currentSendCount == null) {
                            currentSendCount = 0;
                        }
                        webhook.setSendcount(currentSendCount + 1);
                        webhook.setLastsendtime(new Date());
                        
                        // 更新webhook信息
                        boolean updateResult = webhookService.updateById(webhook);
                        if (!updateResult) {
                            logger.warn("更新webhook发送记录失败，webhookId: {}", webhookId);
                        } else {
                            logger.debug("成功更新webhook发送记录，webhookId: {}", webhookId);
                        }
                        
                        successCount++;
                    } catch (Exception e) {
                        logger.error("发送webhook通知失败: {}, 错误: {}, 原因: {}", webhookUrl, e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "无具体原因");
                        e.printStackTrace();
                        failCount++;
                    }
                } else {
                    logger.warn("Webhook未启用或状态不正确，webhookId: {}, 当前状态: {}", webhookId, webhook.getStatus());
                    failCount++;
                }
            }
            
            logger.info("批量通知完成，总处理: {}, 成功: {}, 失败: {}", 
                webhookTableImports.size(), successCount, failCount);
            
            return true;
        } catch (Exception e) {
            logger.error("批量通知过程中出现异常: {}, 堆栈: ", e.getMessage(), e);
            return false;
        }
    }
}