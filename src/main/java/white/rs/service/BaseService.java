package white.rs.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 基础Service接口
 * 继承MyBatis Plus的IService，提供标准CRUD方法
 * IService已经包含了所有常用的CRUD方法：
 * - getById、list、page、save、updateById、removeById等
 *
 * @param <T> 实体类型
 */
public interface BaseService<T> extends IService<T> {
    // 所有CRUD方法已由IService提供，无需重复声明
    // 如需自定义方法，可在子接口中添加
}

