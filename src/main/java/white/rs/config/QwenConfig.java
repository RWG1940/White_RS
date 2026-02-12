package white.rs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通义千问模型配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "qwen")
public class QwenConfig {
    private String apiKey;
    private String baseUrl;
    private Integer imageMaxSize;
    private String imageDefaultSize;
}