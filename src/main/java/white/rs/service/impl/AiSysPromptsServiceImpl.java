package white.rs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.AiSysPrompts;
import white.rs.service.AiSysPromptsService;
import white.rs.mapper.AiSysPromptsMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author Administrator
 * @description 针对表【ai_sys_prompts】的数据库操作Service实现
 * @createDate 2026-01-13 18:02:10
 */
@Service
public class AiSysPromptsServiceImpl extends ServiceImpl<AiSysPromptsMapper, AiSysPrompts>
        implements AiSysPromptsService {

    public WhiteResponse pageBycategory(Long current, Long size, String category) {
        Page<AiSysPrompts> page = new Page<>(current, size);
        QueryWrapper<AiSysPrompts> wrapper = new QueryWrapper<>();

        // 添加筛选条件
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }

        return WhiteResponse.success(baseMapper.selectPage(page, wrapper));
    }
}