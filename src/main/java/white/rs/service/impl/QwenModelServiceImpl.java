package white.rs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.config.QwenConfig;
import white.rs.service.MinioService;
import white.rs.service.QwenModelService;

import java.util.*;

/**
 * 通义千问模型服务实现类
 */
@Service
public class QwenModelServiceImpl implements QwenModelService {

    @Autowired
    private QwenConfig qwenConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MinioService minioService;

    /**
     * 图片编辑接口（基于图片和文本提示进行编辑生成）
     */
    @Override
    public WhiteResponse editImage(String imageUrl, MultipartFile file,String prompt, Integer n, String size) {
        try {
            String apiUrl = "https://dashscope.aliyuncs.com/api/v1/services/aigc/multimodal-generation/generation";

            // 构建消息内容数组
            List<Map<String, Object>> contentList = new ArrayList<>();
            
            // 添加图片内容
            Map<String, Object> imageContent = new HashMap<>();

            // 判断链接或者文件是否存在
            if (imageUrl == null && file.isEmpty()) {
                return WhiteResponse.fail("请上传一张图片");
            }
            if (imageUrl != null && !imageUrl.isEmpty()){
                return WhiteResponse.fail("只能图片文件和链接二选一");
            }
            if (file.isEmpty()){
                System.out.println("url");
                imageContent.put("image", imageUrl);
            }else{
                System.out.println("file");
                // 使用minio服务上传该图片并取得图片公网链接
                String imgUrl = minioService.uploadFile(file, "ai-editing-image", 6L, null).getData().toString();
                // imgUrl 去除前面的/api/files/download/字符串
                imgUrl = imgUrl.substring(imgUrl.indexOf("/api/files/download/") + "/api/files/download/".length());
                // imaUrl 前面拼接cloud.sellersuniononline.com/image/
                System.out.println( "http://cloud.sellersuniononline.com:28888/image/" +imgUrl);
                imageContent.put("image", "http://cloud.sellersuniononline.com:28888/image/" + imgUrl);
            }

            contentList.add(imageContent);
            
            // 添加文本提示内容
            Map<String, Object> textContent = new HashMap<>();
            textContent.put("text", prompt);
            contentList.add(textContent);

            // 构建消息对象
            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", contentList);

            List<Map<String, Object>> messages = Collections.singletonList(message);

            Map<String, Object> input = new HashMap<>();
            input.put("messages", messages);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("n", n);
            parameters.put("negative_prompt", " ");
            parameters.put("prompt_extend", true);
            parameters.put("watermark", false);
            parameters.put("size", size);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "qwen-image-edit-plus-2025-12-15");
            requestBody.put("input", input);
            requestBody.put("parameters", parameters);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + qwenConfig.getApiKey());
            headers.set("User-Agent", "QwenClient/1.0");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("output")) {
                Map<String, Object> output = (Map<String, Object>) responseBody.get("output");

                if (output.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
                    if (!choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        if (choice.containsKey("message")) {
                            Map<String, Object> messageObj = (Map<String, Object>) choice.get("message");
                            if (messageObj.containsKey("content")) {
                                List<Map<String, Object>> contentListResult = (List<Map<String, Object>>) messageObj.get("content");
                                if (contentListResult != null && !contentListResult.isEmpty()) {
                                    List<String> imageUrls = new ArrayList<>();
                                    for (Map<String, Object> c : contentListResult) {
                                        if (c.containsKey("image")) {
                                            String imageUrlResult = (String) c.get("image");
                                            if (imageUrlResult != null && !imageUrlResult.isEmpty()) {
                                                imageUrls.add(imageUrlResult);
                                            }
                                        }
                                    }
                                    
                                    if (!imageUrls.isEmpty()) {
                                        // 如果只有一张图片，返回单个URL；如果多张，返回数组
                                        if (imageUrls.size() == 1) {
                                            return WhiteResponse.success(imageUrls.get(0));
                                        } else {
                                            return WhiteResponse.success(imageUrls);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return WhiteResponse.fail("未返回图片，请检查输入参数或模型配置");
        } catch (Exception e) {
            return WhiteResponse.fail("API调用异常：" + e.getMessage());
        }
    }

    /**
     * 文生图接口（同步调用，按照实际API返回格式）
     */
    @Override
    public WhiteResponse generateImage(String prompt, Integer n, String size) {
        try {
            String apiUrl = "https://dashscope.aliyuncs.com/api/v1/services/aigc/multimodal-generation/generation";

            // 构建消息对象
            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");

            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("text", prompt);

            message.put("content", Collections.singletonList(contentMap));
            List<Map<String, Object>> messages = Collections.singletonList(message);

            Map<String, Object> input = new HashMap<>();
            input.put("messages", messages);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("negative_prompt", "--no (deformed, distorted, disfigured:1.3), bad anatomy, ugly, duplicate, mutated hands, mutated fingers, extra limbs, extra fingers, poorly drawn hands, poorly drawn face, malformed limbs, missing arms, missing legs, fused fingers, too many fingers, long neck, (disfigured:1.2), cloned face, cartoon, 3d, cgi, render, sketch, painting, drawing, anime, doll, plastic, mannequin, wax figure, video game, digital art, unreal, surreal, fantasy, abstract, text, watermark, signature, logo, username, frame, border, blurry, grainy, pixelated, oversaturated, high contrast, oversharpened, soft focus, (symmetrical:1.1), unnatural pose, awkward pose, static pose, perfect skin, airbrushed, makeup, shiny skin, oily skin, unrealistic lighting, studio lighting, flat lighting");
            parameters.put("prompt_extend", true);
            parameters.put("watermark", false);
            parameters.put("size", size);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "qwen-image-max");
            requestBody.put("input", input);
            requestBody.put("parameters", parameters);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + qwenConfig.getApiKey());
            headers.set("User-Agent", "QwenClient/1.0");

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("output")) {
                Map<String, Object> output = (Map<String, Object>) responseBody.get("output");

                if (output.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
                    if (!choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        if (choice.containsKey("message")) {
                            Map<String, Object> messageObj = (Map<String, Object>) choice.get("message");
                            if (messageObj.containsKey("content")) {
                                List<Map<String, Object>> contentList = (List<Map<String, Object>>) messageObj.get("content");
                                if (contentList != null) {
                                    for (Map<String, Object> c : contentList) {
                                        if (c.containsKey("image")) {
                                            String imageUrl = (String) c.get("image");
                                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                                return WhiteResponse.success(imageUrl);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return WhiteResponse.fail("未返回图片，请检查提示词或模型参数");
        } catch (Exception e) {
            return WhiteResponse.fail("API调用异常：" + e.getMessage());
        }
    }
}