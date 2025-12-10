package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.AccessoriesPurchaseContract;
import white.rs.service.AccessoriesPurchaseContractService;


@RestController
@RequestMapping("/acc")
@Api(tags = "辅料购货合同管理")
public class ACCPurchaseContractController extends BaseController<AccessoriesPurchaseContract, AccessoriesPurchaseContractService> {
}

