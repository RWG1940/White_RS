package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.FileShareRequest;
import white.rs.domain.Guest;
import white.rs.service.GuestService;

@RestController
@RequestMapping("/guest")
@Api(tags = "guest管理")
public class GuestController extends BaseController<Guest, GuestService> {
    @Autowired
    private GuestService guestService;
    // 重写添加和修改接口
    @Override
    @ApiOperation("添加客户")
    public WhiteResponse save(
            @RequestBody Guest entity
    ) {
        return guestService.saveGuest(entity);
    }

    @Override
    @ApiOperation("修改客户")
    @PutMapping("/{id}")
    public WhiteResponse updateById(
            @PathVariable Long id,
            @RequestBody Guest entity
    ) {
        return guestService.updateByIdAndGuest(id, entity);
    }


}