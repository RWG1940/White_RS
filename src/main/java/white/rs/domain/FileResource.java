package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 对象存储文件元数据表
 * @TableName file_resource
 */
@TableName(value ="file_resource")
public class FileResource implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 对象存储中的文件key(包含路径)
     */
    private String fileKey;

    /**
     * 文件访问URL（MinIO预签名/反代URL）
     */
    private String fileUrl;

    /**
     * 所属bucket名称
     */
    private String bucket;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件MIME类型，如 image/png
     */
    private String fileType;

    /**
     * 文件扩展名，如 png/jpg/pdf
     */
    private String fileExt;

    /**
     * 业务分类，如 avatar、order, product
     */
    private String bizType;

    /**
     * 业务ID，如用户ID/订单ID
     */
    private Long bizId;

    /**
     * 是否删除（逻辑删）1=已删除
     */
    private Integer isDeleted;

    /**
     * 上传用户ID
     */
    private Long creatorId;

    /**
     * 上传时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 原始文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 原始文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 对象存储中的文件key(包含路径)
     */
    public String getFileKey() {
        return fileKey;
    }

    /**
     * 对象存储中的文件key(包含路径)
     */
    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    /**
     * 文件访问URL（MinIO预签名/反代URL）
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 文件访问URL（MinIO预签名/反代URL）
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * 所属bucket名称
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * 所属bucket名称
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * 文件大小（字节）
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 文件大小（字节）
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 文件MIME类型，如 image/png
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件MIME类型，如 image/png
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 文件扩展名，如 png/jpg/pdf
     */
    public String getFileExt() {
        return fileExt;
    }

    /**
     * 文件扩展名，如 png/jpg/pdf
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    /**
     * 业务分类，如 avatar、order, product
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 业务分类，如 avatar、order, product
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 业务ID，如用户ID/订单ID
     */
    public Long getBizId() {
        return bizId;
    }

    /**
     * 业务ID，如用户ID/订单ID
     */
    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    /**
     * 是否删除（逻辑删）1=已删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除（逻辑删）1=已删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 上传用户ID
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 上传用户ID
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 上传时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 上传时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        FileResource other = (FileResource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFileKey() == null ? other.getFileKey() == null : this.getFileKey().equals(other.getFileKey()))
            && (this.getFileUrl() == null ? other.getFileUrl() == null : this.getFileUrl().equals(other.getFileUrl()))
            && (this.getBucket() == null ? other.getBucket() == null : this.getBucket().equals(other.getBucket()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getFileExt() == null ? other.getFileExt() == null : this.getFileExt().equals(other.getFileExt()))
            && (this.getBizType() == null ? other.getBizType() == null : this.getBizType().equals(other.getBizType()))
            && (this.getBizId() == null ? other.getBizId() == null : this.getBizId().equals(other.getBizId()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileKey() == null) ? 0 : getFileKey().hashCode());
        result = prime * result + ((getFileUrl() == null) ? 0 : getFileUrl().hashCode());
        result = prime * result + ((getBucket() == null) ? 0 : getBucket().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getFileExt() == null) ? 0 : getFileExt().hashCode());
        result = prime * result + ((getBizType() == null) ? 0 : getBizType().hashCode());
        result = prime * result + ((getBizId() == null) ? 0 : getBizId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
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
        sb.append(", fileKey=").append(fileKey);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", bucket=").append(bucket);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileExt=").append(fileExt);
        sb.append(", bizType=").append(bizType);
        sb.append(", bizId=").append(bizId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}