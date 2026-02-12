package white.rs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.DTO.UserBillDTO;
import white.rs.domain.UserBill;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user_bill】的数据库操作Service
* @createDate 2026-02-04 11:11:21
*/
public interface UserBillService extends IService<UserBill> {

    /**
     * 分页查询用户账单信息，包含用户名
     * @param current 当前页码
     * @param size 每页大小
     * @return 包含UserBillDTO的分页结果
     */
    WhiteResponse pageWithUserInfo(Long current, Long size);
}
