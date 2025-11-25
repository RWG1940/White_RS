package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统通知表
 *
 * @TableName notifications
 */
@TableName(value = "notifications")
public class Notifications implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容（可为富文本）
     */
    private String content;

    /**
     * 通知类型：system/message/warning
     */
    private String type;

    /**
     * 接收者用户ID，null表示全体
     */
    private Long receiverUserId;

    /**
     * 阅读时间
     */
    private Date readAt;

    /**
     * 创建时间
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
     * 通知标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 通知标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 通知内容（可为富文本）
     */
    public String getContent() {
        return content;
    }

    /**
     * 通知内容（可为富文本）
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 通知类型：system/message/warning
     */
    public String getType() {
        return type;
    }

    /**
     * 通知类型：system/message/warning
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 接收者用户ID，null表示全体
     */
    public Long getReceiverUserId() {
        return receiverUserId;
    }

    /**
     * 接收者用户ID，null表示全体
     */
    public void setReceiverUserId(Long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    /**
     * 阅读时间
     */
    public Date getReadAt() {
        return readAt;
    }

    /**
     * 阅读时间
     */
    public void setReadAt(Date readAt) {
        this.readAt = readAt;
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
        Notifications other = (Notifications) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getReceiverUserId() == null ? other.getReceiverUserId() == null : this.getReceiverUserId().equals(other.getReceiverUserId()))
                && (this.getReadAt() == null ? other.getReadAt() == null : this.getReadAt().equals(other.getReadAt()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getReceiverUserId() == null) ? 0 : getReceiverUserId().hashCode());
        result = prime * result + ((getReadAt() == null) ? 0 : getReadAt().hashCode());
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
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", receiverUserId=").append(receiverUserId);
        sb.append(", readAt=").append(readAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}