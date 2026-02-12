package white.rs.domain.DTO;

import java.util.Map;

public class SearchDTO {

    private Map<String, String> conditions;

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }
}
