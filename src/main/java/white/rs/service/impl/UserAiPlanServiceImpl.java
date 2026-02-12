package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.DTO.UserPlanDTO;
import white.rs.domain.UserAiPlan;
import white.rs.domain.Users;
import white.rs.domain.AiPlans;
import white.rs.mapper.UserAiPlanMapper;
import white.rs.mapper.UsersMapper;
import white.rs.mapper.AiPlansMapper;
import white.rs.service.UserAiPlanService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【user_ai_plan】的数据库操作Service实现
* @createDate 2026-02-03 15:39:32
*/
@Service
public class UserAiPlanServiceImpl extends ServiceImpl<UserAiPlanMapper, UserAiPlan>
    implements UserAiPlanService{
    
    @Autowired
    private UsersMapper usersMapper;
    
    @Autowired
    private AiPlansMapper aiPlansMapper;

    /**
     * 分页查询用户订阅信息，包含用户名和订阅名
     * @param current 当前页码
     * @param size 每页大小
     * @return 包含UserPlanDTO的分页结果
     */
    @Override
    public WhiteResponse pageWithInfo(Long current, Long size) {
        try {
            // 创建分页对象
            Page<UserAiPlan> page = new Page<>(current, size);
            
            // 执行分页查询
            Page<UserAiPlan> userAiPlanPage = this.page(page);
            
            // 获取用户订阅记录列表
            List<UserAiPlan> userAiPlanList = userAiPlanPage.getRecords();
            
            // 收集所有的用户ID和订阅ID
            List<Long> userIds = userAiPlanList.stream()
                    .map(plan -> plan.getuId().longValue())
                    .distinct()
                    .collect(Collectors.toList());
            
            List<Integer> planIds = userAiPlanList.stream()
                    .map(plan -> plan.getpId())
                    .distinct()
                    .collect(Collectors.toList());
            
            // 批量查询用户信息
            List<Users> usersList = new ArrayList<>();
            if (!userIds.isEmpty()) {
                usersList = usersMapper.selectBatchIds(userIds);
            }
            
            // 批量查询订阅信息
            List<AiPlans> plansList = new ArrayList<>();
            if (!planIds.isEmpty()) {
                plansList = aiPlansMapper.selectBatchIds(new ArrayList<>(planIds));
            }
            
            // 创建映射便于快速查找
            java.util.Map<Long, String> userMap = usersList.stream()
                    .collect(Collectors.toMap(Users::getId, Users::getUsername));
            
            java.util.Map<Integer, String> planMap = plansList.stream()
                    .collect(Collectors.toMap(AiPlans::getId, AiPlans::getName));
            
            // 转换为UserPlanDTO
            List<UserPlanDTO> dtoList = userAiPlanList.stream().map(userAiPlan -> {
                UserPlanDTO dto = new UserPlanDTO();
                dto.setId(userAiPlan.getId().longValue());
                dto.setUserName(userMap.getOrDefault(userAiPlan.getuId().longValue(), "未知用户"));
                dto.setPlanName(planMap.getOrDefault(userAiPlan.getpId(), "未知订阅"));
                dto.setsTime(userAiPlan.getsTime());
                dto.seteTime(userAiPlan.geteTime());
                dto.setStatus(userAiPlan.getStatus());
                return dto;
            }).collect(Collectors.toList());
            
            // 构建返回结果
            Page<UserPlanDTO> resultPage = new Page<>(current, size, userAiPlanPage.getTotal());
            resultPage.setRecords(dtoList);
            
            return WhiteResponse.success(resultPage);
            
        } catch (Exception e) {
            return WhiteResponse.fail("查询用户订阅信息失败: " + e.getMessage());
        }
    }
}