package white.rs.domain.DTO;

import white.rs.domain.Roles;
import white.rs.domain.Users;

import java.io.Serializable;
import java.util.List;

/**
 * 用户及其角色信息 DTO
 * 用于返回带有角色信息的用户列表
 */
public class UserWithRolesDTO implements Serializable {
    /**
     * 用户信息
     */
    private Users user;

    /**
     * 用户的角色列表
     */
    private List<Roles> roles;

    public UserWithRolesDTO() {
    }

    public UserWithRolesDTO(Users user, List<Roles> roles) {
        this.user = user;
        this.roles = roles;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public Users getUser() {
        return user;
    }

    /**
     * 设置用户信息
     *
     * @param user 用户信息
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * 获取用户的角色列表
     *
     * @return 角色列表
     */
    public List<Roles> getRoles() {
        return roles;
    }

    /**
     * 设置用户的角色列表
     *
     * @param roles 角色列表
     */
    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("user=").append(user);
        sb.append(", roles=").append(roles);
        sb.append("]");
        return sb.toString();
    }
}