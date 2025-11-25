package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Menus;


/**
 * @author Administrator
 * @description 针对表【menus(菜单表（前端路由）)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Menus
 */
@Mapper
public interface MenusMapper extends BaseMapper<Menus> {


}
