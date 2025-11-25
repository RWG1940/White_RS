package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Permissions;
import white.rs.service.PermissionsService;
import white.rs.mapper.PermissionsMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【permissions(权限表（权限点）)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions>
        implements PermissionsService {

}
