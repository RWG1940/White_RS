package white.rs.mapper;

import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【feedback】的数据库操作Mapper
* @createDate 2026-01-09 15:36:26
* @Entity white.rs.domain.Feedback
*/
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}




