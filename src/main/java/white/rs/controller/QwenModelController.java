package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.service.QwenModelService;

@Api(tags = "通义千问文生图接口")
@RestController
@RequestMapping("/qwen")
public class QwenModelController {

    @Autowired
    private QwenModelService qwenModelService;

    /**
     * 图片编辑接口（基于图片和文本提示进行编辑生成）
     */
    @ApiOperation("图片编辑接口")
    @PostMapping("/image/edit")
    public WhiteResponse editImage(
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam String prompt,
            @RequestParam(required = false, defaultValue = "1") Integer n,
            @RequestParam(required = false, defaultValue = "1024*1024") String size
    ) {
        return qwenModelService.editImage(imageUrl,imageFile, prompt, n, size);
    }

    /**
     * 文生图接口（同步调用，按照实际API返回格式）
     */
    @ApiOperation("文生图接口")
    @PostMapping("/image/generate")
    public WhiteResponse generateImage(
            @RequestParam String prompt,
            @RequestParam(required = false, defaultValue = "1") Integer n,
            @RequestParam(required = false, defaultValue = "1024*1024") String size
    ) {
        return qwenModelService.generateImage(prompt, n, size);
    }
}