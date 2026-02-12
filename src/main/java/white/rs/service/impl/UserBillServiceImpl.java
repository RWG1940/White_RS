package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.DTO.UserBillDTO;
import white.rs.domain.UserBill;
import white.rs.domain.Users;
import white.rs.mapper.UserBillMapper;
import white.rs.mapper.UsersMapper;
import white.rs.service.UserBillService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【user_bill】的数据库操作Service实现
* @createDate 2026-02-04 11:11:21
*/
@Service
public class UserBillServiceImpl extends ServiceImpl<UserBillMapper, UserBill>
    implements UserBillService{
    
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 分页查询用户账单信息，包含用户名
     * @param current 当前页码
     * @param size 每页大小
     * @return 包含UserBillDTO的分页结果
     */
    @Override
    public WhiteResponse pageWithUserInfo(Long current, Long size) {
        try {
            // 创建分页对象
            Page<UserBill> page = new Page<>(current, size);
            
            // 执行分页查询
            Page<UserBill> userBillPage = this.page(page);
            
            // 获取用户账单记录列表
            List<UserBill> userBillList = userBillPage.getRecords();
            
            // 收集所有的用户ID
            List<Long> userIds = userBillList.stream()
                    .map(bill -> bill.getuId().longValue())
                    .distinct()
                    .collect(Collectors.toList());
            
            // 批量查询用户信息
            List<Users> usersList = new ArrayList<>();
            if (!userIds.isEmpty()) {
                usersList = usersMapper.selectBatchIds(userIds);
            }
            
            // 创建映射便于快速查找
            java.util.Map<Long, String> userMap = usersList.stream()
                    .collect(Collectors.toMap(Users::getId, Users::getUsername));
            
            // 转换为UserBillDTO
            List<UserBillDTO> dtoList = userBillList.stream().map(userBill -> {
                UserBillDTO dto = new UserBillDTO();
                dto.setId(userBill.getId());
                dto.setUserName(userMap.getOrDefault(userBill.getuId().longValue(), "未知用户"));
                dto.setType(userBill.getType());
                dto.setDate(userBill.getDate());
                dto.setOrderNum(userBill.getOrderNum());
                dto.setResult(userBill.getResult());
                dto.setStatus(userBill.getStatus());
                dto.setPaid(userBill.getPaid());
                return dto;
            }).collect(Collectors.toList());
            
            // 构建返回结果
            Page<UserBillDTO> resultPage = new Page<>(current, size, userBillPage.getTotal());
            resultPage.setRecords(dtoList);
            
            return WhiteResponse.success(resultPage);
            
        } catch (Exception e) {
            return WhiteResponse.fail("查询用户账单信息失败: " + e.getMessage());
        }
    }
}