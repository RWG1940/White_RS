package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表（权限点）
 *
 * @TableName permissions
 */
@TableName(value = "permissions")
public class Permissions implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码（唯一，如 user:create）
     */
    private String code;

    /**
     * 权限类型：menu/api/action
     */
    private String type;

    /**
     * API 路径 或 前端路由路径
     */
    private String path;

    /**
     * HTTP方法：GET POST PUT DELETE
     */
    private String method;

    /**
     * 权限扩展信息（JSON）
     */
    private Object meta;

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
     * 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 权限编码（唯一，如 user:create）
     */
    public String getCode() {
        return code;
    }

    /**
     * 权限编码（唯一，如 user:create）
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 权限类型：menu/api/action
     */
    public String getType() {
        return type;
    }

    /**
     * 权限类型：menu/api/action
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * API 路径 或 前端路由路径
     */
    public String getPath() {
        return path;
    }

    /**
     * API 路径 或 前端路由路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * HTTP方法：GET POST PUT DELETE
     */
    public String getMethod() {
        return method;
    }

    /**
     * HTTP方法：GET POST PUT DELETE
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 权限扩展信息（JSON）
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * 权限扩展信息（JSON）
     */
    public void setMeta(Object meta) {
        this.meta = meta;
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
        Permissions other = (Permissions) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
                && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
                && (this.getMeta() == null ? other.getMeta() == null : this.getMeta().equals(other.getMeta()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getMeta() == null) ? 0 : getMeta().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", type=").append(type);
        sb.append(", path=").append(path);
        sb.append(", method=").append(method);
        sb.append(", meta=").append(meta);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}