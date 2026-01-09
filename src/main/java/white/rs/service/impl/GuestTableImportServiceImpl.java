package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.GuestTableImport;
import white.rs.service.GuestTableImportService;
import white.rs.mapper.GuestTableImportMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【guest_table_import】的数据库操作Service实现
* @createDate 2026-01-08 14:25:49
*/
@Service
public class GuestTableImportServiceImpl extends ServiceImpl<GuestTableImportMapper, GuestTableImport>
    implements GuestTableImportService{

    @Override
    public WhiteResponse<GuestTableImport> addGuestTableImport(GuestTableImport entity) {
        // 判断是否已添加过该关联关系
        QueryWrapper<GuestTableImport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("guest_id", entity.getGuestId());
        queryWrapper.eq("import_id", entity.getImportId());
        GuestTableImport guestTableImport = this.getOne(queryWrapper);
        if (guestTableImport == null) {
            this.save(entity);
            return WhiteResponse.success();
        }
        return WhiteResponse.fail("您已添加过该批次");
    }
}




