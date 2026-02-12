package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName user_bill
 */
@TableName(value ="user_bill")
public class UserBill implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uId;

    /**
     * 付费类型
     */
    private String type;

    /**
     * 使用时间
     */
    private Date date;

    /**
     * 支付回执码
     */
    private String orderNum;

    /**
     * 返回的链接或其它结果等
     */
    private String result;

    /**
     * 调用状态
     */
    private Integer status;

    /**
     * 花费
     */
    private BigDecimal paid;

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
     * 付费类型
     */
    public String getType() {
        return type;
    }

    /**
     * 付费类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 使用时间
     */
    public Date getDate() {
        return date;
    }

    /**
     * 使用时间
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 支付回执码
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * 支付回执码
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 返回的链接或其它结果等
     */
    public String getResult() {
        return result;
    }

    /**
     * 返回的链接或其它结果等
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 调用状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 调用状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 花费
     */
    public BigDecimal getPaid() {
        return paid;
    }

    /**
     * 花费
     */
    public void setPaid(BigDecimal paid) {
        this.paid = paid;
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
        UserBill other = (UserBill) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getuId() == null ? other.getuId() == null : this.getuId().equals(other.getuId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPaid() == null ? other.getPaid() == null : this.getPaid().equals(other.getPaid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPaid() == null) ? 0 : getPaid().hashCode());
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
        sb.append(", type=").append(type);
        sb.append(", date=").append(date);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", result=").append(result);
        sb.append(", status=").append(status);
        sb.append(", paid=").append(paid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}