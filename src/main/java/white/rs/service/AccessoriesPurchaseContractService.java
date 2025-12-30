package white.rs.service;

import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.AccessoriesPurchaseContract;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【accessories_purchase_contract(洗标吊牌信息表（支持批量导入，多季度管理）)】的数据库操作Service
* @createDate 2025-12-10 10:59:57
*/
public interface AccessoriesPurchaseContractService extends IService<AccessoriesPurchaseContract> {

    WhiteResponse addFile(MultipartFile file, AccessoriesPurchaseContract acc);

    WhiteResponse updateFile(MultipartFile file, AccessoriesPurchaseContract acc);

    WhiteResponse deleteFile(Long id);

    WhiteResponse importExcel(MultipartFile file,String importId);
    
    void exportExcel(Map<String, Object> body, HttpServletResponse response);
}