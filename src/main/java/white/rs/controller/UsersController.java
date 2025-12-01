package white.rs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import white.rs.controller.base.BaseController;
import white.rs.domain.Users;
import white.rs.service.UsersService;

@RestController
@RequestMapping("/user")
public class UsersController extends BaseController<Users, UsersService> {

}
