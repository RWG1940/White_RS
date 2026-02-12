package white.rs.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import org.springframework.web.bind.annotation.*;
import white.rs.domain.DTO.ExactQueryDTO;
import white.rs.domain.DTO.PageQueryDTO;
import white.rs.domain.DTO.SearchDTO;

import java.util.List;
import java.util.Map;


/**
 * 通用基础控制器
 * 子类只需继承 BaseController<T, S extends IService<T>>
 */
public abstract class BaseController<T, S extends IService<T>> {

    protected final S baseService;

    protected BaseController(S baseService) {
        this.baseService = baseService;
    }

    // ========== CRUD 通用接口 ==========

    @GetMapping("/{id}")
    @ApiOperation("根据Id查询单条数据")
    public WhiteResponse<T> getById(@PathVariable Long id) {
        T entity = baseService.getById(id);
        if (entity == null) {
            return WhiteResponse.fail(ResponseCode.NOT_FOUND);
        }
        return WhiteResponse.success(entity);
    }

    @GetMapping
    @ApiOperation("查询表中所有数据")
    public WhiteResponse<List<T>> list() {
        return WhiteResponse.success(baseService.list());
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public WhiteResponse<Page<T>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size
    ) {
        Page<T> page = new Page<>(current, size);
        baseService.page(page);
        return WhiteResponse.success(page);
    }

    @PostMapping("/query/page")
    @ApiOperation("按筛选条件分页查询")
    public WhiteResponse<Page<T>> pageByConditions(@RequestBody PageQueryDTO dto) {
        return WhiteResponse.success(baseService.page(new Page<>(dto.getCurrent(), dto.getSize()), buildEqWrapper(dto.getConditions())));
    }


    @PostMapping
    @ApiOperation("新增数据")
    public WhiteResponse<T> save(@RequestBody T entity) {
        boolean result = baseService.save(entity);
        return result ? WhiteResponse.success() : WhiteResponse.fail("新增失败");
    }

    @PostMapping("/batch")
    @ApiOperation("批量新增数据")
    public WhiteResponse<Void> saveBatch(@RequestBody List<T> list) {
        if (list == null || list.isEmpty()) {
            return WhiteResponse.fail("列表不能为空");
        }
        return baseService.saveBatch(list) ? WhiteResponse.success() : WhiteResponse.fail("批量新增失败");
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数据")
    public WhiteResponse<T> updateById(@PathVariable Long id, @RequestBody T entity) {
        if (baseService.getById(id) == null) {
            return WhiteResponse.fail(ResponseCode.NOT_FOUND);
        }
        return baseService.updateById(entity)
                ? WhiteResponse.success()
                : WhiteResponse.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除单条数据")
    public WhiteResponse<Void> delete(@PathVariable Long id) {
        if (baseService.getById(id) == null) {
            return WhiteResponse.fail(ResponseCode.NOT_FOUND);
        }
        return baseService.removeById(id)
                ? WhiteResponse.success()
                : WhiteResponse.fail("删除失败");
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除数据")
    public WhiteResponse<Void> deleteBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return WhiteResponse.fail("列表不能为空");
        }
        return baseService.removeByIds(ids)
                ? WhiteResponse.success()
                : WhiteResponse.fail("批量删除失败");
    }

    @GetMapping("/count")
    @ApiOperation("统计表数据条数")
    public WhiteResponse<Long> count() {
        return WhiteResponse.success(baseService.count());
    }


    @PostMapping("/query/like")
    @ApiOperation("模糊查询")
    public WhiteResponse<List<T>> search(@RequestBody SearchDTO dto) {
        return WhiteResponse.success(baseService.list(buildLikeWrapper(dto.getConditions())));
    }


    @PostMapping("/query/eq")
    @ApiOperation("精确查询数据列表")
    public WhiteResponse<List<T>> exact(@RequestBody ExactQueryDTO dto) {
        return WhiteResponse.success(baseService.list(buildEqWrapper(dto.getConditions())));
    }


    protected QueryWrapper<T> buildEqWrapper(Map<String, Object> conditions) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (conditions != null) {
            conditions.forEach((k, v) -> {
                if (StringUtils.hasText(k) && v != null) {
                    wrapper.eq(k, v);
                }
            });
        }
        return wrapper;
    }

    protected QueryWrapper<T> buildLikeWrapper(Map<String, String> conditions) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (conditions != null) {
            conditions.forEach((k, v) -> {
                if (StringUtils.hasText(k) && StringUtils.hasText(v)) {
                    wrapper.like(k, v);
                }
            });
        }
        return wrapper;
    }


}
