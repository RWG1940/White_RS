package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName File_Sharing
 */
@TableName(value ="File_Sharing")
public class FileSharing implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer shareId;

    /**
     * 
     */
    private Long ownerId;

    /**
     * 
     */
    private Long sharedWithUserId;

    /**
     * 
     */
    private String shareLink;

    /**
     * 
     */
    private String sharePassword;

    /**
     * 
     */
    private Object shareType;

    /**
     * 
     */
    private Date shareTime;

    /**
     * 
     */
    private Date expirationTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getShareId() {
        return shareId;
    }

    /**
     * 
     */
    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    /**
     * 
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * 
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 
     */
    public Long getSharedWithUserId() {
        return sharedWithUserId;
    }

    /**
     * 
     */
    public void setSharedWithUserId(Long sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }

    /**
     * 
     */
    public String getShareLink() {
        return shareLink;
    }

    /**
     * 
     */
    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    /**
     * 
     */
    public String getSharePassword() {
        return sharePassword;
    }

    /**
     * 
     */
    public void setSharePassword(String sharePassword) {
        this.sharePassword = sharePassword;
    }

    /**
     * 
     */
    public Object getShareType() {
        return shareType;
    }

    /**
     * 
     */
    public void setShareType(Object shareType) {
        this.shareType = shareType;
    }

    /**
     * 
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * 
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * 
     */
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
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
        FileSharing other = (FileSharing) that;
        return (this.getShareId() == null ? other.getShareId() == null : this.getShareId().equals(other.getShareId()))
            && (this.getOwnerId() == null ? other.getOwnerId() == null : this.getOwnerId().equals(other.getOwnerId()))
            && (this.getSharedWithUserId() == null ? other.getSharedWithUserId() == null : this.getSharedWithUserId().equals(other.getSharedWithUserId()))
            && (this.getShareLink() == null ? other.getShareLink() == null : this.getShareLink().equals(other.getShareLink()))
            && (this.getSharePassword() == null ? other.getSharePassword() == null : this.getSharePassword().equals(other.getSharePassword()))
            && (this.getShareType() == null ? other.getShareType() == null : this.getShareType().equals(other.getShareType()))
            && (this.getShareTime() == null ? other.getShareTime() == null : this.getShareTime().equals(other.getShareTime()))
            && (this.getExpirationTime() == null ? other.getExpirationTime() == null : this.getExpirationTime().equals(other.getExpirationTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShareId() == null) ? 0 : getShareId().hashCode());
        result = prime * result + ((getOwnerId() == null) ? 0 : getOwnerId().hashCode());
        result = prime * result + ((getSharedWithUserId() == null) ? 0 : getSharedWithUserId().hashCode());
        result = prime * result + ((getShareLink() == null) ? 0 : getShareLink().hashCode());
        result = prime * result + ((getSharePassword() == null) ? 0 : getSharePassword().hashCode());
        result = prime * result + ((getShareType() == null) ? 0 : getShareType().hashCode());
        result = prime * result + ((getShareTime() == null) ? 0 : getShareTime().hashCode());
        result = prime * result + ((getExpirationTime() == null) ? 0 : getExpirationTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", shareId=").append(shareId);
        sb.append(", ownerId=").append(ownerId);
        sb.append(", sharedWithUserId=").append(sharedWithUserId);
        sb.append(", shareLink=").append(shareLink);
        sb.append(", sharePassword=").append(sharePassword);
        sb.append(", shareType=").append(shareType);
        sb.append(", shareTime=").append(shareTime);
        sb.append(", expirationTime=").append(expirationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}