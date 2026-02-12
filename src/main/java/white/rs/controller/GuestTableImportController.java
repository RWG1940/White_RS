package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.GuestTableImport;
import white.rs.service.GuestTableImportService;

@RestController
@RequestMapping("/guest-table-import")
@Api(tags = "guest与批次表的关联管理")
public class GuestTableImportController extends BaseController<GuestTableImport, GuestTableImportService> {

    protected final GuestTableImportService service;
    protected GuestTableImportController(GuestTableImportService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping
    @ApiOperation("新增")
    public WhiteResponse<GuestTableImport> save(@RequestBody GuestTableImport entity) {
        return service.addGuestTableImport(entity);
    }
}