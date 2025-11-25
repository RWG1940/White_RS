package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Files;
import white.rs.service.FilesService;
import white.rs.mapper.FilesMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【files(文件上传表)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
        implements FilesService {

}
