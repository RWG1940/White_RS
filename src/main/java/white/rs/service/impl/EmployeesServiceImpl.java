package white.rs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.domain.Employees;
import white.rs.service.EmployeesService;
import white.rs.mapper.EmployeesMapper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【employees(员工表（用户扩展信息）)】的数据库操作Service实现
 * @createDate 2025-11-23 21:26:12
 */
@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees>
        implements EmployeesService {

}
