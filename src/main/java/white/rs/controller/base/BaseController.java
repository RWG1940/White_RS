package white.rs.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 通用基础控制器
 * 子类只需继承 BaseController<T, S extends IService<T>>
 */
public abstract class BaseController<T, S extends IService<T>> {

    @Autowired(required = false)
    protected S baseService;

    // ========== CRUD 通用接口 ==========

    @GetMapping("/{id}")
    @ApiOperation("查询单个")
    public Object getById(@PathVariable Long id) {
        T entity = baseService.getById(id);
        if (entity == null) {
            return fail(ResponseCode.NOT_FOUND);
        }
        return success(entity);
    }

    @GetMapping("/list")
    @ApiOperation("查询列表")
    public Object list() {
        return success(baseService.list());
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Object page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size
    ) {
        Page<T> page = new Page<>(current, size);
        page = baseService.page(page);
        return success(baseService.page(page));
    }

    @PostMapping
    @ApiOperation("新增")
    public WhiteResponse<T> save(@RequestBody T entity) {
        boolean result = baseService.save(entity);
        return result ? success("新增成功", entity) : fail("新增失败");
    }

    @PostMapping("/batch")
    @ApiOperation("批量新增")
    public WhiteResponse<Void> saveBatch(@RequestBody List<T> list) {
        if (list == null || list.isEmpty()) {
            return paramError("数据列表不能为空");
        }
        return baseService.saveBatch(list) ? success("批量新增成功") : fail("批量新增失败");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新")
    public WhiteResponse<T> updateById(@PathVariable Long id, @RequestBody T entity) {
        if (baseService.getById(id) == null) {
            return fail(ResponseCode.NOT_FOUND);
        }
        return baseService.updateById(entity)
                ? success("更新成功", entity)
                : fail("更新失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public WhiteResponse<Void> delete(@PathVariable Long id) {
        if (baseService.getById(id) == null) {
            return fail(ResponseCode.NOT_FOUND);
        }
        return baseService.removeById(id)
                ? success("删除成功")
                : fail("删除失败");
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除")
    public WhiteResponse<Void> deleteBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return paramError("ID列表不能为空");
        }
        return baseService.removeByIds(ids)
                ? success("批量删除成功")
                : fail("批量删除失败");
    }

    @GetMapping("/count")
    @ApiOperation("统计数量")
    public Object count() {
        return success(baseService.count());
    }

    // 模糊查询接口
    @GetMapping("/search")
    @ApiOperation("模糊查询")
    public Object search(@RequestParam String column,
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

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.like(column, keyword);
        if (column1 != null && keyword1 != null) {
            wrapper.like(column1, keyword1);
        }
        if (column2 != null && keyword2 != null) {
            wrapper.like(column2, keyword2);
        }
        if (column3 != null && keyword3 != null) {
            wrapper.like(column3, keyword3);
        }
        if (column4 != null && keyword4 != null) {
            wrapper.like(column4, keyword4);
        }
        if (column5 != null && keyword5 != null) {
            wrapper.like(column5, keyword5);
        }
        List<T> list = baseService.list(wrapper);
        return success(list);
    }

    // 精确查询接口
    @GetMapping("/exact")
    @ApiOperation("精确查询")
    public Object exact(@RequestParam String column,
                        @RequestParam String keyword) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(column, keyword);
        return success(baseService.list(wrapper));
    }

    // ========== 响应封装 ==========

    protected <R> WhiteResponse<R> success() {
        return WhiteResponse.success();
    }

    protected <R> WhiteResponse<R> success(R data) {
        return WhiteResponse.success(data);
    }

    protected <R> WhiteResponse<R> success(String msg, R data) {
        return WhiteResponse.success(msg, data);
    }

    protected WhiteResponse<Void> success(String msg) {
        return WhiteResponse.success(msg, null);
    }

    protected <R> WhiteResponse<R> fail() {
        return WhiteResponse.fail();
    }

    protected <R> WhiteResponse<R> fail(String msg) {
        return WhiteResponse.fail(msg);
    }

    protected <R> WhiteResponse<R> fail(Integer code, String msg) {
        return WhiteResponse.fail(code, msg);
    }

    protected <R> WhiteResponse<R> fail(ResponseCode code) {
        return WhiteResponse.fail(code);
    }

    protected <R> WhiteResponse<R> paramError(String msg) {
        return WhiteResponse.fail(ResponseCode.PARAM_ERROR.getCode(), msg);
    }

    protected <R> WhiteResponse<R> unauthorized() {
        return WhiteResponse.fail(ResponseCode.UNAUTHORIZED);
    }

    protected <R> WhiteResponse<R> forbidden() {
        return WhiteResponse.fail(ResponseCode.FORBIDDEN);
    }

    protected <R> WhiteResponse<R> notFound() {
        return WhiteResponse.fail(ResponseCode.NOT_FOUND);
    }
}
