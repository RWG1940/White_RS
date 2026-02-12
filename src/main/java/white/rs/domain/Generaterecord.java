package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName generateRecord
 */
@TableName(value ="generateRecord")
public class Generaterecord implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private Date createAt;

    /**
     * 
     */
    private Integer uid;

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
    public String getType() {
        return type;
    }

    /**
     * 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 
     */
    public void setUid(Integer uid) {
        this.uid = uid;
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
        Generaterecord other = (Generaterecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getCreateAt() == null ? other.getCreateAt() == null : this.getCreateAt().equals(other.getCreateAt()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", url=").append(url);
        sb.append(", createAt=").append(createAt);
        sb.append(", uid=").append(uid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}