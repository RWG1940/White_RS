package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName guest_table_import
 */
@TableName(value ="guest_table_import")
public class GuestTableImport implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer guestId;

    /**
     * 
     */
    private Integer importId;

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
    public Integer getGuestId() {
        return guestId;
    }

    /**
     * 
     */
    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    /**
     * 
     */
    public Integer getImportId() {
        return importId;
    }

    /**
     * 
     */
    public void setImportId(Integer importId) {
        this.importId = importId;
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
        GuestTableImport other = (GuestTableImport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGuestId() == null ? other.getGuestId() == null : this.getGuestId().equals(other.getGuestId()))
            && (this.getImportId() == null ? other.getImportId() == null : this.getImportId().equals(other.getImportId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGuestId() == null) ? 0 : getGuestId().hashCode());
        result = prime * result + ((getImportId() == null) ? 0 : getImportId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", guestId=").append(guestId);
        sb.append(", importId=").append(importId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}