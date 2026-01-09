package white.rs.service;

import white.rs.common.response.WhiteResponse;
import white.rs.domain.GuestTableImport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【guest_table_import】的数据库操作Service
* @createDate 2026-01-08 14:25:49
*/
public interface GuestTableImportService extends IService<GuestTableImport> {

    WhiteResponse<GuestTableImport> addGuestTableImport(GuestTableImport entity);
}
