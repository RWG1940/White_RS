package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户表
 * @TableName users
 */
@TableName(value ="users")
public class Users implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名（唯一）
     */
    private String username;

    /**
     * 邮箱（唯一，可为空）
     */
    private String email;

    /**
     * 手机号（唯一，可为空）
     */
    private String phone;

    /**
     * 密码哈希（bcrypt/argon2）
     */
    private String passwordHash;

    /**
     * 账号状态：0禁用 1正常 2锁定
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private Date lastLoginAt;

    /**
     * 上次登录IP
     */
    private String lastLoginIp;

    /**
     * 扩展字段（JSON）
     */
    private Object profile;

    /**
     * 版本号（乐观锁）
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 软删除时间
     */
    private Date deletedAt;

    /**
     * 
     */
    private String avatarUrl;

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
     * 用户名（唯一）
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名（唯一）
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 邮箱（唯一，可为空）
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱（唯一，可为空）
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 手机号（唯一，可为空）
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机号（唯一，可为空）
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 密码哈希（bcrypt/argon2）
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * 密码哈希（bcrypt/argon2）
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * 账号状态：0禁用 1正常 2锁定
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 账号状态：0禁用 1正常 2锁定
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 上次登录时间
     */
    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * 上次登录时间
     */
    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * 上次登录IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 上次登录IP
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 扩展字段（JSON）
     */
    public Object getProfile() {
        return profile;
    }

    /**
     * 扩展字段（JSON）
     */
    public void setProfile(Object profile) {
        this.profile = profile;
    }

    /**
     * 版本号（乐观锁）
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 版本号（乐观锁）
     */
    public void setVersion(Integer version) {
        this.version = version;
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
     * 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 软删除时间
     */
    public Date getDeletedAt() {
        return deletedAt;
    }

    /**
     * 软删除时间
     */
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * 
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
        Users other = (Users) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getPasswordHash() == null ? other.getPasswordHash() == null : this.getPasswordHash().equals(other.getPasswordHash()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getLastLoginAt() == null ? other.getLastLoginAt() == null : this.getLastLoginAt().equals(other.getLastLoginAt()))
            && (this.getLastLoginIp() == null ? other.getLastLoginIp() == null : this.getLastLoginIp().equals(other.getLastLoginIp()))
            && (this.getProfile() == null ? other.getProfile() == null : this.getProfile().equals(other.getProfile()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()))
            && (this.getDeletedAt() == null ? other.getDeletedAt() == null : this.getDeletedAt().equals(other.getDeletedAt()))
            && (this.getAvatarUrl() == null ? other.getAvatarUrl() == null : this.getAvatarUrl().equals(other.getAvatarUrl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getPasswordHash() == null) ? 0 : getPasswordHash().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getLastLoginAt() == null) ? 0 : getLastLoginAt().hashCode());
        result = prime * result + ((getLastLoginIp() == null) ? 0 : getLastLoginIp().hashCode());
        result = prime * result + ((getProfile() == null) ? 0 : getProfile().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        result = prime * result + ((getDeletedAt() == null) ? 0 : getDeletedAt().hashCode());
        result = prime * result + ((getAvatarUrl() == null) ? 0 : getAvatarUrl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", passwordHash=").append(passwordHash);
        sb.append(", status=").append(status);
        sb.append(", lastLoginAt=").append(lastLoginAt);
        sb.append(", lastLoginIp=").append(lastLoginIp);
        sb.append(", profile=").append(profile);
        sb.append(", version=").append(version);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", deletedAt=").append(deletedAt);
        sb.append(", avatarUrl=").append(avatarUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}