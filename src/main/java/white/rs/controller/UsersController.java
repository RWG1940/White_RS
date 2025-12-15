package white.rs.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.DTO.UserWithRolesDTO;
import white.rs.domain.Users;
import white.rs.service.UsersService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理控制器
 * 注意：类级别的 @PreAuthorize 不会自动应用到继承的方法
 * 需要在每个方法上单独添加 @PreAuthorize 注解
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UsersController extends BaseController<Users, UsersService> {

    @Autowired
    private UsersService usersService;


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/list")
    @ApiOperation("查询用户及其角色列表")
    @Override
    public Object list() {
        List<UserWithRolesDTO> userList = usersService.listUsersWithRoles();
        return WhiteResponse.success(userList);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/{id}")
    @ApiOperation("查询单个用户及其角色")
    @Override
    public Object getById(@PathVariable Long id) {
        UserWithRolesDTO userWithRoles = usersService.getUserWithRolesById(id);
        if (userWithRoles == null) {
            return WhiteResponse.fail("用户不存在");
        }
        return WhiteResponse.success(userWithRoles);
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/page")
    @ApiOperation("分页查询用户及其角色")
    @Override
    public Object page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size
    ) {
        // 创建分页对象
        IPage<Users> userPage = new Page<>(current, size);
        
        // 查询用户分页数据
        IPage<Users> pageResult = usersService.page(userPage);
        
        // 转换为UserWithRolesDTO分页数据
        IPage<UserWithRolesDTO> dtoPage = new Page<>(current, size, pageResult.getTotal());
        
        // 获取用户ID列表
        List<Long> userIds = pageResult.getRecords().stream()
                .map(Users::getId)
                .collect(Collectors.toList());
        
        // 批量查询用户及其角色
        List<UserWithRolesDTO> userWithRolesList = usersService.listUsersWithRolesByIds(userIds);
        dtoPage.setRecords(userWithRolesList);
        
        return WhiteResponse.success(dtoPage);
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PostMapping("/dto")
    @ApiOperation("新增用户及其角色")
    public WhiteResponse<UserWithRolesDTO> saveUserWithRoles(
            @RequestBody Users entity,
            @RequestParam(required = false) List<Long> roleIds) {
        boolean saved = usersService.saveUserWithRoles(entity, roleIds);
        if (saved) {
            UserWithRolesDTO userWithRoles = usersService.getUserWithRolesById(entity.getId());
            return WhiteResponse.success(userWithRoles);
        }
        return WhiteResponse.fail("保存失败");
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PutMapping("/dto/{id}")
    @ApiOperation("更新用户及其角色")
    public WhiteResponse<UserWithRolesDTO> updateById(
            @PathVariable Long id,
            @RequestBody Users entity,
            @RequestParam(required = false) List<Long> roleIds) {
        entity.setId(id);
        boolean updated = usersService.updateUserWithRoles(entity, roleIds);
        if (updated) {
            UserWithRolesDTO userWithRoles = usersService.getUserWithRolesById(id);
            return WhiteResponse.success(userWithRoles);
        }
        return WhiteResponse.fail("更新失败");
    }


    @Override
    @PreAuthorize("hasAuthority('ROLE_3294')")
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户及其角色关系")
    public WhiteResponse<Void> delete(@PathVariable Long id) {
        boolean removed = usersService.removeUserWithRoles(id);
        if (removed) {
            return WhiteResponse.success();
        }
        return WhiteResponse.fail("删除失败");
    }


    @Override
    @PreAuthorize("hasAuthority('ROLE_3294')")
    @DeleteMapping("/batch")
    @ApiOperation("批量删除用户及其角色关系")
    public WhiteResponse<Void> deleteBatch(@RequestBody List<Long> ids) {
        boolean removed = usersService.removeUsersWithRoles(ids);
        if (removed) {
            return WhiteResponse.success();
        }
        return WhiteResponse.fail("删除失败");
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/count")
    @ApiOperation("统计数量")
    @Override
    public Object count() {
        return super.count();
    }



    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/search")
    @ApiOperation("模糊查询用户及其角色")
    @Override
    public Object search(
            @RequestParam String column,
            @RequestParam String keyword) {
        // 构建查询条件
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(column, keyword);
        
        // 查询用户列表
        List<Users> users = usersService.list(queryWrapper);
        
        // 获取用户ID列表
        List<Long> userIds = users.stream()
                .map(Users::getId)
                .collect(Collectors.toList());
        
        // 批量查询用户及其角色
        List<UserWithRolesDTO> userWithRolesList = usersService.listUsersWithRolesByIds(userIds);
        
        return WhiteResponse.success(userWithRolesList);
    }
}