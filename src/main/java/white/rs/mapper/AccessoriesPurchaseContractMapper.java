package white.rs.mapper;

import org.apache.ibatis.annotations.Mapper;
import white.rs.domain.AccessoriesPurchaseContract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【accessories_purchase_contract(洗标吊牌信息表（支持批量导入，多季度管理）)】的数据库操作Mapper
* @createDate 2025-12-10 10:59:57
* @Entity white.rs.domain.AccessoriesPurchaseContract
*/
@Mapper
public interface AccessoriesPurchaseContractMapper extends BaseMapper<AccessoriesPurchaseContract> {

}




