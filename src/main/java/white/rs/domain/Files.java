package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传表
 *
 * @TableName files
 */
@TableName(value = "files")
public class Files implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 存储路径或对象存储KEY
     */
    private String storageKey;

    /**
     * 文件大小（字节）
     */
    private Long size;

    /**
     * 文件类型
     */
    private String mimeType;

    /**
     * 文件哈希（用于去重）
     */
    private String hash;

    /**
     * 存储类型：local/s3/minio
     */
    private String storage;

    /**
     * 上传者ID
     */
    private Long uploaderId;

    /**
     * 上传时间
     */
    private Date createdAt;

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
    public String getOriginalName() {
        return originalName;
    }

    /**
     * 原始文件名
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * 存储路径或对象存储KEY
     */
    public String getStorageKey() {
        return storageKey;
    }

    /**
     * 存储路径或对象存储KEY
     */
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    /**
     * 文件大小（字节）
     */
    public Long getSize() {
        return size;
    }

    /**
     * 文件大小（字节）
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * 文件类型
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * 文件类型
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * 文件哈希（用于去重）
     */
    public String getHash() {
        return hash;
    }

    /**
     * 文件哈希（用于去重）
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * 存储类型：local/s3/minio
     */
    public String getStorage() {
        return storage;
    }

    /**
     * 存储类型：local/s3/minio
     */
    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * 上传者ID
     */
    public Long getUploaderId() {
        return uploaderId;
    }

    /**
     * 上传者ID
     */
    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    /**
     * 上传时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 上传时间
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
        Files other = (Files) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getOriginalName() == null ? other.getOriginalName() == null : this.getOriginalName().equals(other.getOriginalName()))
                && (this.getStorageKey() == null ? other.getStorageKey() == null : this.getStorageKey().equals(other.getStorageKey()))
                && (this.getSize() == null ? other.getSize() == null : this.getSize().equals(other.getSize()))
                && (this.getMimeType() == null ? other.getMimeType() == null : this.getMimeType().equals(other.getMimeType()))
                && (this.getHash() == null ? other.getHash() == null : this.getHash().equals(other.getHash()))
                && (this.getStorage() == null ? other.getStorage() == null : this.getStorage().equals(other.getStorage()))
                && (this.getUploaderId() == null ? other.getUploaderId() == null : this.getUploaderId().equals(other.getUploaderId()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOriginalName() == null) ? 0 : getOriginalName().hashCode());
        result = prime * result + ((getStorageKey() == null) ? 0 : getStorageKey().hashCode());
        result = prime * result + ((getSize() == null) ? 0 : getSize().hashCode());
        result = prime * result + ((getMimeType() == null) ? 0 : getMimeType().hashCode());
        result = prime * result + ((getHash() == null) ? 0 : getHash().hashCode());
        result = prime * result + ((getStorage() == null) ? 0 : getStorage().hashCode());
        result = prime * result + ((getUploaderId() == null) ? 0 : getUploaderId().hashCode());
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
        sb.append(", originalName=").append(originalName);
        sb.append(", storageKey=").append(storageKey);
        sb.append(", size=").append(size);
        sb.append(", mimeType=").append(mimeType);
        sb.append(", hash=").append(hash);
        sb.append(", storage=").append(storage);
        sb.append(", uploaderId=").append(uploaderId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}