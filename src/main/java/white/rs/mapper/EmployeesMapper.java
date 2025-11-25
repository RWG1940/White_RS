package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Employees;


/**
 * @author Administrator
 * @description 针对表【employees(员工表（用户扩展信息）)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Employees
 */
@Mapper
public interface EmployeesMapper extends BaseMapper<Employees> {


}
