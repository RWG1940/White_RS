package white.rs.domain.DTO;

import java.util.Map;

public class ExactQueryDTO {

    private Map<String, Object> conditions;

    public Map<String, Object> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }
}
