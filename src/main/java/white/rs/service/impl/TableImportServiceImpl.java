package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.AccessoriesPurchaseContract;
import white.rs.domain.TableImport;
import white.rs.service.AccessoriesPurchaseContractService;
import white.rs.service.TableImportService;
import white.rs.mapper.TableImportMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【table_import】的数据库操作Service实现
* @createDate 2025-12-15 17:22:29
*/
@Service
public class TableImportServiceImpl extends ServiceImpl<TableImportMapper, TableImport>
    implements TableImportService{


}




