package white.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.Roles;
import white.rs.service.RolesService;


@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController extends BaseController<Roles, RolesService> {
}

