package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Notifications;

/**
 * @author Administrator
 * @description 针对表【notifications(系统通知表)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Notifications
 */
@Mapper
public interface NotificationsMapper extends BaseMapper<Notifications> {


}
