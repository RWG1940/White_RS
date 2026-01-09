package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName File_Sharing_Files
 */
@TableName(value ="File_Sharing_Files")
public class FileSharingFiles implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer shareId;

    /**
     * 
     */
    private Long fileId;

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
    public Long getFileId() {
        return fileId;
    }

    /**
     * 
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
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
        FileSharingFiles other = (FileSharingFiles) that;
        return (this.getShareId() == null ? other.getShareId() == null : this.getShareId().equals(other.getShareId()))
            && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShareId() == null) ? 0 : getShareId().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", shareId=").append(shareId);
        sb.append(", fileId=").append(fileId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}