package white.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.Departments;


/**
 * @author Administrator
 * @description 针对表【departments(部门表（组织架构）)】的数据库操作Mapper
 * @createDate 2025-11-23 21:26:12
 * @Entity white.rs.domain.Departments
 */
@Mapper
public interface DepartmentsMapper extends BaseMapper<Departments> {


}
