package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.DictType;


/**
 * @author Administrator
 * @description 针对表【dict_type(字典类型表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.DictType
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {


}
