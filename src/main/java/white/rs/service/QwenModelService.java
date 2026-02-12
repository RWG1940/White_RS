package white.rs.service;

import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;

/**
 * 通义千问模型服务接口
 */
public interface QwenModelService {

    /**
     * 文生图接口
     * 
     * @param prompt 提示词
     * @param n 生成图片数量
     * @param size 图片尺寸
     * @return 生成的图片URL或URL列表
     */
    WhiteResponse generateImage(String prompt, Integer n, String size);

    /**
     * 图片编辑接口（基于图片和文本提示进行编辑生成）
     * 
     * @param imageUrl 原始图片URL
     * @param prompt 编辑提示词
     * @param n 生成图片数量
     * @param size 图片尺寸
     * @return 编辑后的图片URL或URL列表
     */
    WhiteResponse editImage(String imageUrl, MultipartFile file, String prompt, Integer n, String size);
}