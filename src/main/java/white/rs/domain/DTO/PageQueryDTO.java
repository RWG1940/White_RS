package white.rs.domain.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class PageQueryDTO {
    private Long current = 1L;
    private Long size = 10L;
    private Map<String, Object> conditions;
}
