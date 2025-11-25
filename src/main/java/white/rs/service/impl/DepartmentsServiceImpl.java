package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Departments;
import white.rs.service.DepartmentsService;
import white.rs.mapper.DepartmentsMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【departments(部门表（组织架构）)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments>
        implements DepartmentsService {

}
