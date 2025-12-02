package white.rs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
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

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/{id}")
    @ApiOperation("查询单个")
    @Override
    public WhiteResponse<Users> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/list")
    @ApiOperation("查询列表")
    @Override
    public WhiteResponse<List<Users>> list() {
        return super.list();
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/page")
    @ApiOperation("分页查询")
    @Override
    public WhiteResponse<IPage<Users>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size
    ) {
        return super.page(current, size);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PostMapping
    @ApiOperation("新增")
    @Override
    public WhiteResponse<Users> save(@RequestBody Users entity) {
        return super.save(entity);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PostMapping("/batch")
    @ApiOperation("批量新增")
    @Override
    public WhiteResponse<Void> saveBatch(@RequestBody List<Users> list) {
        return super.saveBatch(list);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @PutMapping("/{id}")
    @ApiOperation("更新")
    @Override
    public WhiteResponse<Users> updateById(@PathVariable Long id, @RequestBody Users entity) {
        return super.updateById(id, entity);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @Override
    public WhiteResponse<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @DeleteMapping("/batch")
    @ApiOperation("批量删除")
    @Override
    public WhiteResponse<Void> deleteBatch(@RequestBody List<Long> ids) {
        return super.deleteBatch(ids);
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/count")
    @ApiOperation("统计数量")
    @Override
    public WhiteResponse<Long> count() {
        return super.count();
    }

    @PreAuthorize("hasAuthority('ROLE_3294')")
    @GetMapping("/search")
    @ApiOperation("模糊查询")
    @Override
    public WhiteResponse<List<Users>> search(@RequestParam String column,
                                           @RequestParam String keyword) {
        return super.search(column, keyword);
    }
}
