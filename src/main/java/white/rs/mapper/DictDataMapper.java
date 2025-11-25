package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.DictData;


/**
 * @author Administrator
 * @description 针对表【dict_data(字典数据表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.DictData
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {


}
