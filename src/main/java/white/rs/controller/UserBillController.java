package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.UserBill;
import white.rs.service.UserBillService;


@RestController
@RequestMapping("/user-bill")
@Api(tags = "用户帐单")
public class UserBillController extends BaseController<UserBill, UserBillService> {

    protected final UserBillService service;
    protected UserBillController(UserBillService service) {
        super(service);
        this.service = service;
    }
    
    /**
     * 分页查询用户账单信息，包含用户名
     * @param current 当前页码
     * @param size 每页大小
     * @return 分页结果
     */
    @Override
    @GetMapping("/page")
    @ApiOperation("分页查询用户账单信息（包含用户名）")
    public WhiteResponse page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        return service.pageWithUserInfo(current, size);
    }
}