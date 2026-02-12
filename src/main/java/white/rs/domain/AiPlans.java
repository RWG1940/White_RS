package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName ai_plans
 */
@TableName(value ="ai_plans")
public class AiPlans implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 订阅名
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 天数
     */
    private Integer durationDays;

    /**
     * 每日调用上限
     */
    private Integer dailyLimit;

    /**
     * 最大图片尺寸
     */
    private String maxSize;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 免费次数
     */
    private Integer cheapLimit;

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
     * 订阅名
     */
    public String getName() {
        return name;
    }

    /**
     * 订阅名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 天数
     */
    public Integer getDurationDays() {
        return durationDays;
    }

    /**
     * 天数
     */
    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    /**
     * 每日调用上限
     */
    public Integer getDailyLimit() {
        return dailyLimit;
    }

    /**
     * 每日调用上限
     */
    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    /**
     * 最大图片尺寸
     */
    public String getMaxSize() {
        return maxSize;
    }

    /**
     * 最大图片尺寸
     */
    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 免费次数
     */
    public Integer getCheapLimit() {
        return cheapLimit;
    }

    /**
     * 免费次数
     */
    public void setCheapLimit(Integer cheapLimit) {
        this.cheapLimit = cheapLimit;
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
        AiPlans other = (AiPlans) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDurationDays() == null ? other.getDurationDays() == null : this.getDurationDays().equals(other.getDurationDays()))
            && (this.getDailyLimit() == null ? other.getDailyLimit() == null : this.getDailyLimit().equals(other.getDailyLimit()))
            && (this.getMaxSize() == null ? other.getMaxSize() == null : this.getMaxSize().equals(other.getMaxSize()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCheapLimit() == null ? other.getCheapLimit() == null : this.getCheapLimit().equals(other.getCheapLimit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDurationDays() == null) ? 0 : getDurationDays().hashCode());
        result = prime * result + ((getDailyLimit() == null) ? 0 : getDailyLimit().hashCode());
        result = prime * result + ((getMaxSize() == null) ? 0 : getMaxSize().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCheapLimit() == null) ? 0 : getCheapLimit().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", durationDays=").append(durationDays);
        sb.append(", dailyLimit=").append(dailyLimit);
        sb.append(", maxSize=").append(maxSize);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", status=").append(status);
        sb.append(", description=").append(description);
        sb.append(", cheapLimit=").append(cheapLimit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}