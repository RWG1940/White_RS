package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志表
 *
 * @TableName login_logs
 */
@TableName(value = "login_logs")
public class LoginLogs implements Serializable {
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
     * 用户名（快照）
     */
    private String username;

    /**
     * 结果：1成功 0失败
     */
    private Integer status;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录提示信息
     */
    private String message;

    /**
     * 登录时间
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
     * 用户名（快照）
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名（快照）
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 结果：1成功 0失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 结果：1成功 0失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 登录IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 登录IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 登录提示信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 登录提示信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 登录时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 登录时间
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
        LoginLogs other = (LoginLogs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
                && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
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
        sb.append(", userId=").append(userId);
        sb.append(", username=").append(username);
        sb.append(", status=").append(status);
        sb.append(", ip=").append(ip);
        sb.append(", message=").append(message);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}