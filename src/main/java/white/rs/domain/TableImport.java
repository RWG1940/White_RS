package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName table_import
 */
@TableName(value ="table_import")
public class TableImport implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String fileName;

    /**
     * 
     */
    private String period;

    /**
     * 
     */
    private String importUser;

    /**
     * 
     */
    private Date createdAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 
     */
    public String getImportUser() {
        return importUser;
    }

    /**
     * 
     */
    public void setImportUser(String importUser) {
        this.importUser = importUser;
    }

    /**
     * 
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        TableImport other = (TableImport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getPeriod() == null ? other.getPeriod() == null : this.getPeriod().equals(other.getPeriod()))
            && (this.getImportUser() == null ? other.getImportUser() == null : this.getImportUser().equals(other.getImportUser()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getPeriod() == null) ? 0 : getPeriod().hashCode());
        result = prime * result + ((getImportUser() == null) ? 0 : getImportUser().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileName=").append(fileName);
        sb.append(", period=").append(period);
        sb.append(", importUser=").append(importUser);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}