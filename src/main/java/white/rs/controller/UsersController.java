package white.rs.controller;

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
    public WhiteResponse list() {
        return WhiteResponse.success(usersService.listUsersWithRoles());
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/{id}")
    @ApiOperation("查询单个用户及其角色")
    @Override
    public Object getById(@PathVariable Long id) {
        return WhiteResponse.success(usersService.getUserWithRolesById(id));
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/page")
    @ApiOperation("分页查询用户及其角色")
    @Override
    public Object page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size
    ) {
        return WhiteResponse.success(usersService.pageUsersWithRoles(current, size));
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PostMapping("/dto")
    @ApiOperation("新增用户及其角色")
    public WhiteResponse<UserWithRolesDTO> saveUserWithRoles(
            @RequestBody Users entity,
            @RequestParam(required = false) List<Long> roleIds) {
        return usersService.saveUserWithRolesAndReturnResponse(entity, roleIds);
    }


    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PutMapping("/dto/{id}")
    @ApiOperation("更新用户及其角色")
    public WhiteResponse<UserWithRolesDTO> updateById(
            @PathVariable Long id,
            @RequestBody Users entity,
            @RequestParam(required = false) List<Long> roleIds) {
        entity.setId(id);
        return usersService.updateUserWithRolesAndReturnResponse(entity, roleIds);
    }


    @Override
    @PreAuthorize("hasAuthority('ROLE_3294')")
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户及其角色关系")
    public WhiteResponse<Void> delete(@PathVariable Long id) {
        return usersService.removeUserWithRolesAndReturnResponse(id);
    }


    @Override
    @PreAuthorize("hasAuthority('ROLE_3294')")
    @DeleteMapping("/batch")
    @ApiOperation("批量删除用户及其角色关系")
    public WhiteResponse<Void> deleteBatch(@RequestBody List<Long> ids) {
        return usersService.removeUsersWithRolesAndReturnResponse(ids);
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
            @RequestParam String keyword,
            @RequestParam(required = false) String column1,
            @RequestParam(required = false) String keyword1,
            @RequestParam(required = false) String column2,
            @RequestParam(required = false) String keyword2,
            @RequestParam(required = false) String column3,
            @RequestParam(required = false) String keyword3,
            @RequestParam(required = false) String column4,
            @RequestParam(required = false) String keyword4,
            @RequestParam(required = false) String column5,
            @RequestParam(required = false) String keyword5
    ) {
        return WhiteResponse.success(usersService.searchUsersWithRoles(column, keyword));
    }
}