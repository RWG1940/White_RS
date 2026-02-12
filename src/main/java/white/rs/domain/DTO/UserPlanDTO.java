package white.rs.domain.DTO;

import java.util.Date;

public class UserPlanDTO {
    private Long id;
    private String userName;
    private String planName;
    private Date sTime;
    private Date eTime;
    private int status;
    
    // 构造函数
    public UserPlanDTO() {}
    
    public UserPlanDTO(Long id, String userName, String planName, Date sTime, Date eTime, int status) {
        this.id = id;
        this.userName = userName;
        this.planName = planName;
        this.sTime = sTime;
        this.eTime = eTime;
        this.status = status;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPlanName() {
        return planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public Date getsTime() {
        return sTime;
    }
    
    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }
    
    public Date geteTime() {
        return eTime;
    }
    
    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "UserPlanDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", planName='" + planName + '\'' +
                ", sTime=" + sTime +
                ", eTime=" + eTime +
                ", status=" + status +
                '}';
    }
}
