package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.common.response.ResponseCode;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.TableImport;
import white.rs.service.TableImportService;
@RestController
@RequestMapping("/tableImport")
@Api(tags = "导入批次管理")
public class TableImportController extends BaseController<TableImport, TableImportService> {

}
