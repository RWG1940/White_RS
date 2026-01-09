package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.Guest;
import white.rs.service.GuestService;
import white.rs.mapper.GuestMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Administrator
 * @description 针对表【guest】的数据库操作Service实现
 * @createDate 2026-01-08 15:48:51
 */
@Service
public class GuestServiceImpl extends ServiceImpl<GuestMapper, Guest>
        implements GuestService{

    @Override
    public WhiteResponse updateByIdAndGuest(Long id, Guest entity) {
        Guest guest = this.getById(id);
        guest.setUpdatedAt(new Date());
        this.updateById(entity);
        return WhiteResponse.success("更新成功");
    }

    @Override
    public WhiteResponse saveGuest(Guest entity) {
        Guest guest = new Guest();
        guest.setName(entity.getName());
        guest.setCreatedAt(new Date());
        guest.setUpdatedAt(new Date());
        guest.setStatus(1);
        this.save(guest);
        return WhiteResponse.success("添加成功");
    }
}




