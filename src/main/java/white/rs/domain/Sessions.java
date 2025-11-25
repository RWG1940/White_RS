package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户会话表（管理登录Token）
 *
 * @TableName sessions
 */
@TableName(value = "sessions")
public class Sessions implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 会话Token（如JWT），或Token哈希
     */
    private String token;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 设备信息/浏览器UA
     */
    private String device;

    /**
     * 登录时间
     */
    private Date loginAt;

    /**
     * 过期时间
     */
    private Date expiresAt;

    /**
     * 是否已注销：0未注销 1已注销
     */
    private Integer revoked;

    /**
     * 注销时间（手动退出或被踢）
     */
    private Date revokedAt;

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
     * 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 会话Token（如JWT），或Token哈希
     */
    public String getToken() {
        return token;
    }

    /**
     * 会话Token（如JWT），或Token哈希
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 登录IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 登录IP地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 设备信息/浏览器UA
     */
    public String getDevice() {
        return device;
    }

    /**
     * 设备信息/浏览器UA
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * 登录时间
     */
    public Date getLoginAt() {
        return loginAt;
    }

    /**
     * 登录时间
     */
    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    /**
     * 过期时间
     */
    public Date getExpiresAt() {
        return expiresAt;
    }

    /**
     * 过期时间
     */
    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * 是否已注销：0未注销 1已注销
     */
    public Integer getRevoked() {
        return revoked;
    }

    /**
     * 是否已注销：0未注销 1已注销
     */
    public void setRevoked(Integer revoked) {
        this.revoked = revoked;
    }

    /**
     * 注销时间（手动退出或被踢）
     */
    public Date getRevokedAt() {
        return revokedAt;
    }

    /**
     * 注销时间（手动退出或被踢）
     */
    public void setRevokedAt(Date revokedAt) {
        this.revokedAt = revokedAt;
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
        Sessions other = (Sessions) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
                && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
                && (this.getDevice() == null ? other.getDevice() == null : this.getDevice().equals(other.getDevice()))
                && (this.getLoginAt() == null ? other.getLoginAt() == null : this.getLoginAt().equals(other.getLoginAt()))
                && (this.getExpiresAt() == null ? other.getExpiresAt() == null : this.getExpiresAt().equals(other.getExpiresAt()))
                && (this.getRevoked() == null ? other.getRevoked() == null : this.getRevoked().equals(other.getRevoked()))
                && (this.getRevokedAt() == null ? other.getRevokedAt() == null : this.getRevokedAt().equals(other.getRevokedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getDevice() == null) ? 0 : getDevice().hashCode());
        result = prime * result + ((getLoginAt() == null) ? 0 : getLoginAt().hashCode());
        result = prime * result + ((getExpiresAt() == null) ? 0 : getExpiresAt().hashCode());
        result = prime * result + ((getRevoked() == null) ? 0 : getRevoked().hashCode());
        result = prime * result + ((getRevokedAt() == null) ? 0 : getRevokedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", token=").append(token);
        sb.append(", ip=").append(ip);
        sb.append(", device=").append(device);
        sb.append(", loginAt=").append(loginAt);
        sb.append(", expiresAt=").append(expiresAt);
        sb.append(", revoked=").append(revoked);
        sb.append(", revokedAt=").append(revokedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}