package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.TableImport;
import white.rs.service.TableImportService;
@RestController
@RequestMapping("/tableImport")
@Api(tags = "导入批次管理")
public class TableImportController extends BaseController<TableImport, TableImportService> {
}
