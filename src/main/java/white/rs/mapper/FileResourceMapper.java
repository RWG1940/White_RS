package white.rs.mapper;

import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.FileResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【file_resource(对象存储文件元数据表)】的数据库操作Mapper
* @createDate 2025-12-09 09:09:41
* @Entity white.rs.domain.FileResource
*/
@Mapper
public interface FileResourceMapper extends BaseMapper<FileResource> {

}




