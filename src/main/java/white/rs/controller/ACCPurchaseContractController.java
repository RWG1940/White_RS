package white.rs.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.AccessoriesPurchaseContract;
import white.rs.service.AccessoriesPurchaseContractService;
import white.rs.service.impl.AccessoriesPurchaseContractServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/acc")
@Api(tags = "辅料购货合同管理")
public class ACCPurchaseContractController extends BaseController<AccessoriesPurchaseContract, AccessoriesPurchaseContractService> {

    @Autowired
    private AccessoriesPurchaseContractServiceImpl service;

    @Override
    @PostMapping
    @ApiOperation("单条记录添加")
    public WhiteResponse save(
            @ApiParam(value = "单条记录(JSON 字符串)", required = true)
            @RequestBody AccessoriesPurchaseContract acc
    ) {
        return service.save(acc)?WhiteResponse.success():WhiteResponse.fail();
    }




    // 默认添加和修改方法只是用于文字录入，故对文件上传的记录重新实现接口
    @PostMapping(value = "/addFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("单条记录添加文件")
    public WhiteResponse addFile(
            @ApiParam(value = "文件", required = true)
            @RequestPart("file") MultipartFile file,

            @ApiParam(value = "单条记录(JSON 字符串)", required = true)
            @RequestPart("acc") String accJson
    ) {
        // JSON → 对象
        AccessoriesPurchaseContract acc = JSON.parseObject(accJson, AccessoriesPurchaseContract.class);
        return service.addFile(file, acc);
    }

    @PostMapping(value = "/updateFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("单条记录修改文件")
    public WhiteResponse updateFile(
            @ApiParam(value = "文件", required = true)
            @RequestPart("file") MultipartFile file,

            @ApiParam(value = "单条记录(JSON 字符串)", required = true)
            @RequestPart("acc") String accJson
    ) {
        // 将传入的JSON字符串"accJson"解析为AccessoriesPurchaseContract类的对象，以便后续处理
        AccessoriesPurchaseContract acc = JSON.parseObject(accJson, AccessoriesPurchaseContract.class);
        return service.updateFile(file, acc);
    }

    // 删除记录，将会一并删除文件
    @Override
    @DeleteMapping("/{id}")
    @ApiOperation("单条记录删除")
    public WhiteResponse delete(
            @ApiParam(value = "单条记录的id", required = true)
            @PathVariable Long id
    ) {

        return service.deleteFile(id);
    }

    // 导入
    @PostMapping(value = "/importExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("从 Excel 导入辅料购货合同数据")
    public WhiteResponse importExcel(
            @ApiParam(value = "Excel 文件", required = true)
            @RequestPart("file") MultipartFile file,
            @ApiParam(value = "导入的标识", required = true)
            @RequestPart("importId") String importId
    ) {
        return service.importExcel(file, importId);
    }
    
    // 导出
    @PostMapping("/exportExcel")
    @ApiOperation("导出辅料购货合同数据到 Excel")
    public void exportExcel(
            @RequestBody Map<String, Object> body,
            
            HttpServletResponse response) {
        // 从请求体中获取参数
        String keyword = (String) body.get("keyword");
        // 获取排序字段
        String sortBy = (String) body.get("sortBy");
        // 获取排序参数
        String sortOrder = (String) body.get("sortOrder");
        Object exportIdsObj = body.get("exportIds");
        String importIds;
        
        // 处理 exportIds 可能是 ArrayList、String 或 Number 的情况
        if (exportIdsObj instanceof String) {
            importIds = (String) exportIdsObj;
        } else if (exportIdsObj instanceof List) {
            // 如果是 List，则将其转换为 JSON 字符串
            importIds = JSON.toJSONString(exportIdsObj);
        } else if (exportIdsObj instanceof Number) {
            // 如果是单个数字，构造一个包含该数字的数组字符串
            importIds = "[" + exportIdsObj.toString() + "]";
        } else {
            importIds = exportIdsObj != null ? exportIdsObj.toString() : null;
        }
        
        // 将importIds参数转换为Long列表
        List<Long> importIdList = new ArrayList<>();
        if (importIds != null && !importIds.isEmpty()) {
            try {
                importIdList = JSON.parseArray(importIds, Long.class);
            } catch (Exception e) {
                // 如果解析失败，尝试处理单个数字的情况
                try {
                    Long singleId = JSON.parseObject(importIds, Long.class);
                    importIdList.add(singleId);
                } catch (Exception ex) {
                    // 忽略异常，使用空列表
                    System.err.println("Failed to parse exportIds: " + ex.getMessage());
                }
            }
        }
        
        // 构建查询条件
        QueryWrapper<AccessoriesPurchaseContract> queryWrapper = new QueryWrapper<>();
        
        // 如果有搜索关键字，则添加搜索条件
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like("sku", keyword)
                    .or()
                    .like("color", keyword)
                    .or()
                    .like("brand", keyword)
                    .or()
                    .like("name_en", keyword)
                    .or()
                    .like("factory", keyword)
                    .or()
                    .like("follower", keyword)
            );
        }
        
        // 如果有排序字段，则添加排序条件
        if (sortBy != null && !sortBy.isEmpty()) {
            if ("desc".equalsIgnoreCase(sortOrder)) {
                queryWrapper.orderByDesc(sortBy);
            } else {
                queryWrapper.orderByAsc(sortBy);
            }
        }
        // 根据批次id列表查询
        System.out.println("原始值"+importIds);
        System.out.println("批次id列表：" + importIdList);
        queryWrapper.in(importIdList != null && !importIdList.isEmpty(), "import_id", importIdList);
        List<AccessoriesPurchaseContract> contractList = service.list(queryWrapper);
        System.out.println("查询到的数据条数：" + (contractList != null ? contractList.size() : 0));
        service.exportExcel(contractList, response);
    }

}