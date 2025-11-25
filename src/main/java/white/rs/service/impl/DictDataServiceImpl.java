package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.DictData;
import white.rs.service.DictDataService;
import white.rs.mapper.DictDataMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【dict_data(字典数据表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData>
        implements DictDataService {

}
