package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Settings;

/**
 * @author Administrator
 * @description 针对表【settings(系统配置表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Settings
 */
@Mapper
public interface SettingsMapper extends BaseMapper<Settings> {


}
