package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Menus;
import white.rs.service.MenusService;
import white.rs.mapper.MenusMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【menus(菜单表（前端路由）)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus>
        implements MenusService {

}
