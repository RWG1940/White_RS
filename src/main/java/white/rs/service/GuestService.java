package white.rs.service;

import white.rs.common.response.WhiteResponse;
import white.rs.domain.Guest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【guest】的数据库操作Service
* @createDate 2026-01-08 16:31:23
*/
public interface GuestService extends IService<Guest> {

    WhiteResponse saveGuest(Guest entity);

    WhiteResponse updateByIdAndGuest(Long id, Guest entity);
}
