package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.service.BaseService;

/**
 * 基础Service实现类
 * 继承MyBatis Plus的ServiceImpl，实现BaseService接口
 * 所有CRUD方法已由ServiceImpl提供，无需重复实现
 *
 * @param <M> Mapper类型
 * @param <T> 实体类型
 */
public class BaseServiceImpl<M extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, T>
        extends ServiceImpl<M, T> implements BaseService<T> {
    // 所有CRUD方法已由ServiceImpl提供，无需重复实现
    // 如需自定义方法，可在子类中重写或添加
}

