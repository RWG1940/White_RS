package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName webhook_table_import
 */
@TableName(value ="webhook_table_import")
public class WebhookTableImport implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private Integer webhookId;

    /**
     * 批次id
     */
    private Integer importId;

    /**
     * 备注
     */
    private String remark;

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
    public Integer getWebhookId() {
        return webhookId;
    }

    /**
     * 
     */
    public void setWebhookId(Integer webhookId) {
        this.webhookId = webhookId;
    }

    /**
     * 批次id
     */
    public Integer getImportId() {
        return importId;
    }

    /**
     * 批次id
     */
    public void setImportId(Integer importId) {
        this.importId = importId;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
        WebhookTableImport other = (WebhookTableImport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWebhookId() == null ? other.getWebhookId() == null : this.getWebhookId().equals(other.getWebhookId()))
            && (this.getImportId() == null ? other.getImportId() == null : this.getImportId().equals(other.getImportId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWebhookId() == null) ? 0 : getWebhookId().hashCode());
        result = prime * result + ((getImportId() == null) ? 0 : getImportId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", webhookId=").append(webhookId);
        sb.append(", importId=").append(importId);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}