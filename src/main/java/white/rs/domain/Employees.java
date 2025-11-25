package white.rs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工表（用户扩展信息）
 *
 * @TableName employees
 */
@TableName(value = "employees")
public class Employees implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的用户ID（唯一）
     */
    private Long userId;

    /**
     * 所属部门ID
     */
    private Long departmentId;

    /**
     * 职位名称，如：后端工程师/管理员
     */
    private String jobTitle;

    /**
     * 工号（可唯一）
     */
    private String employeeNo;

    /**
     * 入职日期
     */
    private Date hireDate;

    /**
     * 状态：1在职 0离职
     */
    private Integer status;

    /**
     * 员工扩展信息（JSON）
     */
    private Object extra;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

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
     * 关联的用户ID（唯一）
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 关联的用户ID（唯一）
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 所属部门ID
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * 所属部门ID
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 职位名称，如：后端工程师/管理员
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * 职位名称，如：后端工程师/管理员
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * 工号（可唯一）
     */
    public String getEmployeeNo() {
        return employeeNo;
    }

    /**
     * 工号（可唯一）
     */
    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    /**
     * 入职日期
     */
    public Date getHireDate() {
        return hireDate;
    }

    /**
     * 入职日期
     */
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * 状态：1在职 0离职
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态：1在职 0离职
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 员工扩展信息（JSON）
     */
    public Object getExtra() {
        return extra;
    }

    /**
     * 员工扩展信息（JSON）
     */
    public void setExtra(Object extra) {
        this.extra = extra;
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

    /**
     * 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
        Employees other = (Employees) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
                && (this.getJobTitle() == null ? other.getJobTitle() == null : this.getJobTitle().equals(other.getJobTitle()))
                && (this.getEmployeeNo() == null ? other.getEmployeeNo() == null : this.getEmployeeNo().equals(other.getEmployeeNo()))
                && (this.getHireDate() == null ? other.getHireDate() == null : this.getHireDate().equals(other.getHireDate()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
                && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getJobTitle() == null) ? 0 : getJobTitle().hashCode());
        result = prime * result + ((getEmployeeNo() == null) ? 0 : getEmployeeNo().hashCode());
        result = prime * result + ((getHireDate() == null) ? 0 : getHireDate().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
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
        sb.append(", departmentId=").append(departmentId);
        sb.append(", jobTitle=").append(jobTitle);
        sb.append(", employeeNo=").append(employeeNo);
        sb.append(", hireDate=").append(hireDate);
        sb.append(", status=").append(status);
        sb.append(", extra=").append(extra);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}