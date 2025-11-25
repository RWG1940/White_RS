package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Settings;
import white.rs.service.SettingsService;
import white.rs.mapper.SettingsMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【settings(系统配置表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Settings>
        implements SettingsService {

}
