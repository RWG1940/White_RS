package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.DictType;
import white.rs.service.DictTypeService;
import white.rs.mapper.DictTypeMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【dict_type(字典类型表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType>
        implements DictTypeService {

}
