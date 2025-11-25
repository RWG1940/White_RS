package white.rs.controller.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import white.rs.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础控制器
 * 提供通用的CRUD接口，子类只需继承并注入对应的Service即可
 *
 * @param <T> 实体类型
 * @param <S> Service类型
 */
public abstract class BaseController<T, S extends BaseService<T>> {

    /**
     * Service实例
     * 子类继承后，Spring会自动注入对应的Service实现
     * 使用required=false避免在抽象类中报错，实际运行时子类会正确注入
     */
    @Autowired(required = false)
    protected S baseService;

    /**
     * 根据ID查询
     * GET /{id}
     */
    @GetMapping("/{id}")
    public WhiteResponse<T> getById(@PathVariable Long id) {
        T entity = baseService.getById(id);
        if (entity == null) {
            return fail(ResponseCode.NOT_FOUND);
        }
        return success(entity);
    }

    /**
     * 查询所有
     * GET /list
     */
    @GetMapping("/list")
    public WhiteResponse<List<T>> list() {
        List<T> list = baseService.list();
        return success(list);
    }

    /**
     * 分页查询
     * GET /page?current=1&size=10
     */
    @GetMapping("/page")
    public WhiteResponse<IPage<T>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<T> page = new Page<>(current, size);
        IPage<T> pageResult = baseService.page(page);
        return success(pageResult);
    }

    /**
     * 新增
     * POST /
     */
    @PostMapping
    public WhiteResponse<T> save(@RequestBody T entity) {
        boolean result = baseService.save(entity);
        if (result) {
            return success("新增成功", entity);
        }
        return fail("新增失败");
    }

    /**
     * 批量新增
     * POST /batch
     */
    @PostMapping("/batch")
    public WhiteResponse<Void> saveBatch(@RequestBody List<T> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return paramError("数据列表不能为空");
        }
        boolean result = baseService.saveBatch(entityList);
        if (result) {
            return success("批量新增成功");
        }
        return fail("批量新增失败");
    }

    /**
     * 根据ID更新
     * PUT /{id}
     */
    @PutMapping("/{id}")
    public WhiteResponse<T> updateById(@PathVariable Long id, @RequestBody T entity) {
        // 检查实体是否存在
        T existingEntity = baseService.getById(id);
        if (existingEntity == null) {
            return fail(ResponseCode.NOT_FOUND);
        }
        boolean result = baseService.updateById(entity);
        if (result) {
            return success("更新成功", entity);
        }
        return fail("更新失败");
    }

    /**
     * 根据ID删除
     * DELETE /{id}
     */
    @DeleteMapping("/{id}")
    public WhiteResponse<Void> removeById(@PathVariable Long id) {
        T existingEntity = baseService.getById(id);
        if (existingEntity == null) {
            return fail(ResponseCode.NOT_FOUND);
        }
        boolean result = baseService.removeById(id);
        if (result) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    /**
     * 批量删除
     * DELETE /batch
     */
    @DeleteMapping("/batch")
    public WhiteResponse<Void> removeByIds(@RequestBody List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return paramError("ID列表不能为空");
        }
        boolean result = baseService.removeByIds(idList);
        if (result) {
            return success("批量删除成功");
        }
        return fail("批量删除失败");
    }

    /**
     * 统计总数
     * GET /count
     */
    @GetMapping("/count")
    public WhiteResponse<Long> count() {
        long count = baseService.count();
        return success(count);
    }

    // ========== 响应封装方法 ==========

    /**
     * 成功响应（无数据）
     */
    protected <R> WhiteResponse<R> success() {
        return WhiteResponse.success();
    }

    /**
     * 成功响应（带数据）
     */
    protected <R> WhiteResponse<R> success(R data) {
        return WhiteResponse.success(data);
    }

    /**
     * 成功响应（自定义消息）
     */
    protected <R> WhiteResponse<R> success(String message, R data) {
        return WhiteResponse.success(message, data);
    }

    /**
     * 成功响应（仅消息，无数据，返回Void类型）
     */
    protected WhiteResponse<Void> success(String message) {
        return WhiteResponse.success(message, null);
    }

    /**
     * 失败响应
     */
    protected <R> WhiteResponse<R> fail() {
        return WhiteResponse.fail();
    }

    /**
     * 失败响应（自定义消息）
     */
    protected <R> WhiteResponse<R> fail(String message) {
        return WhiteResponse.fail(message);
    }

    /**
     * 失败响应（仅消息，无数据，返回Void类型）
     */
    protected WhiteResponse<Void> failVoid(String message) {
        return WhiteResponse.fail(message);
    }

    /**
     * 失败响应（自定义码和消息）
     */
    protected <R> WhiteResponse<R> fail(Integer code, String message) {
        return WhiteResponse.fail(code, message);
    }

    /**
     * 失败响应（使用响应码枚举）
     */
    protected <R> WhiteResponse<R> fail(ResponseCode responseCode) {
        return WhiteResponse.fail(responseCode);
    }

    /**
     * 参数错误响应
     */
    protected <R> WhiteResponse<R> paramError(String message) {
        return WhiteResponse.fail(ResponseCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 未授权响应
     */
    protected <R> WhiteResponse<R> unauthorized() {
        return WhiteResponse.fail(ResponseCode.UNAUTHORIZED);
    }

    /**
     * 禁止访问响应
     */
    protected <R> WhiteResponse<R> forbidden() {
        return WhiteResponse.fail(ResponseCode.FORBIDDEN);
    }

    /**
     * 资源不存在响应
     */
    protected <R> WhiteResponse<R> notFound() {
        return WhiteResponse.fail(ResponseCode.NOT_FOUND);
    }
}

