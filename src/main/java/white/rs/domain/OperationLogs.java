package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表
 *
 * @TableName operation_logs
 */
@TableName(value = "operation_logs")
public class OperationLogs implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名（快照）
     */
    private String username;

    /**
     * 操作动作，如 UPDATE/DELETE
     */
    private String action;

    /**
     * 操作目标表名
     */
    private String targetTable;

    /**
     * 目标记录ID
     */
    private String targetId;

    /**
     * 变更前数据（JSON）
     */
    private Object beforeState;

    /**
     * 变更后数据（JSON）
     */
    private Object afterState;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 客户端UA
     */
    private String userAgent;

    /**
     * 操作时间
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
     * 操作用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 操作用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 操作用户名（快照）
     */
    public String getUsername() {
        return username;
    }

    /**
     * 操作用户名（快照）
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 操作动作，如 UPDATE/DELETE
     */
    public String getAction() {
        return action;
    }

    /**
     * 操作动作，如 UPDATE/DELETE
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 操作目标表名
     */
    public String getTargetTable() {
        return targetTable;
    }

    /**
     * 操作目标表名
     */
    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    /**
     * 目标记录ID
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 目标记录ID
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 变更前数据（JSON）
     */
    public Object getBeforeState() {
        return beforeState;
    }

    /**
     * 变更前数据（JSON）
     */
    public void setBeforeState(Object beforeState) {
        this.beforeState = beforeState;
    }

    /**
     * 变更后数据（JSON）
     */
    public Object getAfterState() {
        return afterState;
    }

    /**
     * 变更后数据（JSON）
     */
    public void setAfterState(Object afterState) {
        this.afterState = afterState;
    }

    /**
     * 操作IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 操作IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 客户端UA
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 客户端UA
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 操作时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 操作时间
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
        OperationLogs other = (OperationLogs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
                && (this.getTargetTable() == null ? other.getTargetTable() == null : this.getTargetTable().equals(other.getTargetTable()))
                && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
                && (this.getBeforeState() == null ? other.getBeforeState() == null : this.getBeforeState().equals(other.getBeforeState()))
                && (this.getAfterState() == null ? other.getAfterState() == null : this.getAfterState().equals(other.getAfterState()))
                && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
                && (this.getUserAgent() == null ? other.getUserAgent() == null : this.getUserAgent().equals(other.getUserAgent()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getTargetTable() == null) ? 0 : getTargetTable().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getBeforeState() == null) ? 0 : getBeforeState().hashCode());
        result = prime * result + ((getAfterState() == null) ? 0 : getAfterState().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getUserAgent() == null) ? 0 : getUserAgent().hashCode());
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
        sb.append(", action=").append(action);
        sb.append(", targetTable=").append(targetTable);
        sb.append(", targetId=").append(targetId);
        sb.append(", beforeState=").append(beforeState);
        sb.append(", afterState=").append(afterState);
        sb.append(", ip=").append(ip);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}