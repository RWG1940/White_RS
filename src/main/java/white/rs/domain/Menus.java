package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 菜单表（前端路由）
 *
 * @TableName menus
 */
@TableName(value = "menus")
public class Menus implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 前端组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer orderNum;

    /**
     * 是否隐藏：0显示 1隐藏
     */
    private Integer hidden;

    /**
     * 关联权限编码
     */
    private String permissionCode;

    /**
     * 菜单元数据（JSON）
     */
    private Object meta;

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
     * 父菜单ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父菜单ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 路由路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 路由路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 前端组件路径
     */
    public String getComponent() {
        return component;
    }

    /**
     * 前端组件路径
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 排序号
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 排序号
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 是否隐藏：0显示 1隐藏
     */
    public Integer getHidden() {
        return hidden;
    }

    /**
     * 是否隐藏：0显示 1隐藏
     */
    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    /**
     * 关联权限编码
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 关联权限编码
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    /**
     * 菜单元数据（JSON）
     */
    public Object getMeta() {
        return meta;
    }

    /**
     * 菜单元数据（JSON）
     */
    public void setMeta(Object meta) {
        this.meta = meta;
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
        Menus other = (Menus) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
                && (this.getComponent() == null ? other.getComponent() == null : this.getComponent().equals(other.getComponent()))
                && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
                && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
                && (this.getHidden() == null ? other.getHidden() == null : this.getHidden().equals(other.getHidden()))
                && (this.getPermissionCode() == null ? other.getPermissionCode() == null : this.getPermissionCode().equals(other.getPermissionCode()))
                && (this.getMeta() == null ? other.getMeta() == null : this.getMeta().equals(other.getMeta()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getComponent() == null) ? 0 : getComponent().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getHidden() == null) ? 0 : getHidden().hashCode());
        result = prime * result + ((getPermissionCode() == null) ? 0 : getPermissionCode().hashCode());
        result = prime * result + ((getMeta() == null) ? 0 : getMeta().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", path=").append(path);
        sb.append(", component=").append(component);
        sb.append(", icon=").append(icon);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", hidden=").append(hidden);
        sb.append(", permissionCode=").append(permissionCode);
        sb.append(", meta=").append(meta);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}