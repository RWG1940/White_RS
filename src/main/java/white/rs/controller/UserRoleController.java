package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.UserRole;
import white.rs.service.UserRoleService;


@RestController
@RequestMapping("/user-role")
@Api(tags = "用户角色管理")
public class UserRoleController extends BaseController<UserRole, UserRoleService> {
}