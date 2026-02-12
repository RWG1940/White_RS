package white.rs.mapper;

import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.UserBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【user_bill】的数据库操作Mapper
* @createDate 2026-02-04 11:11:21
* @Entity white.rs.domain.UserBill
*/
@Mapper
public interface UserBillMapper extends BaseMapper<UserBill> {

}




