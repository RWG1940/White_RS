package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Files;


/**
 * @author Administrator
 * @description 针对表【files(文件上传表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Files
 */
@Mapper
public interface FilesMapper extends BaseMapper<Files> {


}
