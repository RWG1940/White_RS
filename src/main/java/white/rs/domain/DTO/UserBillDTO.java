package white.rs.domain.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class UserBillDTO {
    private Integer id;
    private String userName;
    private String type;
    private Date date;
    private String orderNum;
    private String result;
    private Integer status;
    private BigDecimal paid;
    
    // 构造函数
    public UserBillDTO() {}
    
    public UserBillDTO(Integer id, String userName, String type, Date date, 
                      String orderNum, String result, Integer status, BigDecimal paid) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.date = date;
        this.orderNum = orderNum;
        this.result = result;
        this.status = status;
        this.paid = paid;
    }
    
    // Getter和Setter方法
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public BigDecimal getPaid() {
        return paid;
    }
    
    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
    
    @Override
    public String toString() {
        return "UserBillDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", orderNum='" + orderNum + '\'' +
                ", result='" + result + '\'' +
                ", status=" + status +
                ", paid=" + paid +
                '}';
    }
}