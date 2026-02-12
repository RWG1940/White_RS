package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName ai_sys_prompts
 */
@TableName(value ="ai_sys_prompts")
public class AiSysPrompts implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String label;

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    private String value;

    /**
     * 
     */
    private String describtion;

    /**
     * 
     */
    private String subcategory;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     */
    public String getDescribtion() {
        return describtion;
    }

    /**
     * 
     */
    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    /**
     * 
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * 
     */
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AiSysPrompts other = (AiSysPrompts) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getDescribtion() == null ? other.getDescribtion() == null : this.getDescribtion().equals(other.getDescribtion()))
            && (this.getSubcategory() == null ? other.getSubcategory() == null : this.getSubcategory().equals(other.getSubcategory()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getDescribtion() == null) ? 0 : getDescribtion().hashCode());
        result = prime * result + ((getSubcategory() == null) ? 0 : getSubcategory().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", label=").append(label);
        sb.append(", category=").append(category);
        sb.append(", value=").append(value);
        sb.append(", describtion=").append(describtion);
        sb.append(", subcategory=").append(subcategory);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}