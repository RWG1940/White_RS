package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_ai_plan
 */
@TableName(value ="user_ai_plan")
public class UserAiPlan implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 用户id
     */
    private Integer uId;

    /**
     * 订阅id
     */
    private Integer pId;

    /**
     * 开始时间
     */
    private Date sTime;

    /**
     * 结束时间
     */
    private Date eTime;

    /**
     * 订阅状态
     */
    private Integer status;

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
     * 用户id
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 用户id
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * 订阅id
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * 订阅id
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }

    /**
     * 开始时间
     */
    public Date getsTime() {
        return sTime;
    }

    /**
     * 开始时间
     */
    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    /**
     * 结束时间
     */
    public Date geteTime() {
        return eTime;
    }

    /**
     * 结束时间
     */
    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    /**
     * 订阅状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 订阅状态
     */
    public void setStatus(Integer status) {
        this.status = status;
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
        UserAiPlan other = (UserAiPlan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getuId() == null ? other.getuId() == null : this.getuId().equals(other.getuId()))
            && (this.getpId() == null ? other.getpId() == null : this.getpId().equals(other.getpId()))
            && (this.getsTime() == null ? other.getsTime() == null : this.getsTime().equals(other.getsTime()))
            && (this.geteTime() == null ? other.geteTime() == null : this.geteTime().equals(other.geteTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getpId() == null) ? 0 : getpId().hashCode());
        result = prime * result + ((getsTime() == null) ? 0 : getsTime().hashCode());
        result = prime * result + ((geteTime() == null) ? 0 : geteTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uId=").append(uId);
        sb.append(", pId=").append(pId);
        sb.append(", sTime=").append(sTime);
        sb.append(", eTime=").append(eTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}