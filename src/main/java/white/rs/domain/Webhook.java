package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName webhook
 */
@TableName(value ="webhook")
public class Webhook implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Webhook 名称
     */
    private String name;

    /**
     * Webhook 地址
     */
    private String url;

    /**
     * 启用/禁用
     */
    private Integer status;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdat;

    /**
     * 更新时间
     */
    private Date updatedat;

    /**
     * 推送次数
     */
    private Integer sendcount;

    /**
     * 最近一次推送结果
     */
    private Date lastsendtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Webhook 名称
     */
    public String getName() {
        return name;
    }

    /**
     * Webhook 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Webhook 地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * Webhook 地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 启用/禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 启用/禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 创建时间
     */
    public Date getCreatedat() {
        return createdat;
    }

    /**
     * 创建时间
     */
    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    /**
     * 更新时间
     */
    public Date getUpdatedat() {
        return updatedat;
    }

    /**
     * 更新时间
     */
    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    /**
     * 推送次数
     */
    public Integer getSendcount() {
        return sendcount;
    }

    /**
     * 推送次数
     */
    public void setSendcount(Integer sendcount) {
        this.sendcount = sendcount;
    }

    /**
     * 最近一次推送结果
     */
    public Date getLastsendtime() {
        return lastsendtime;
    }

    /**
     * 最近一次推送结果
     */
    public void setLastsendtime(Date lastsendtime) {
        this.lastsendtime = lastsendtime;
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
        Webhook other = (Webhook) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreatedat() == null ? other.getCreatedat() == null : this.getCreatedat().equals(other.getCreatedat()))
            && (this.getUpdatedat() == null ? other.getUpdatedat() == null : this.getUpdatedat().equals(other.getUpdatedat()))
            && (this.getSendcount() == null ? other.getSendcount() == null : this.getSendcount().equals(other.getSendcount()))
            && (this.getLastsendtime() == null ? other.getLastsendtime() == null : this.getLastsendtime().equals(other.getLastsendtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreatedat() == null) ? 0 : getCreatedat().hashCode());
        result = prime * result + ((getUpdatedat() == null) ? 0 : getUpdatedat().hashCode());
        result = prime * result + ((getSendcount() == null) ? 0 : getSendcount().hashCode());
        result = prime * result + ((getLastsendtime() == null) ? 0 : getLastsendtime().hashCode());
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
        sb.append(", url=").append(url);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", remark=").append(remark);
        sb.append(", createdat=").append(createdat);
        sb.append(", updatedat=").append(updatedat);
        sb.append(", sendcount=").append(sendcount);
        sb.append(", lastsendtime=").append(lastsendtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}