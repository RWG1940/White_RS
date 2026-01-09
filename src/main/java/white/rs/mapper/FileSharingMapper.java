package white.rs.mapper;

import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.FileSharing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【File_Sharing】的数据库操作Mapper
* @createDate 2026-01-07 15:26:21
* @Entity white.rs.domain.FileSharing
*/
@Mapper
public interface FileSharingMapper extends BaseMapper<FileSharing> {

}




