package white.rs.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.AccessoriesPurchaseContract;
import white.rs.service.AccessoriesPurchaseContractService;
import white.rs.service.impl.AccessoriesPurchaseContractServiceImpl;

import javax.servlet.http.HttpServletResponse;
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
        return service.save(acc) ? WhiteResponse.success() : WhiteResponse.fail();
    }


    // 默认添加和修改方法只是用于文字录入，故对包含文件上传的记录重新实现接口
    @PostMapping(value = "/addFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("单条记录添加文件")
    public WhiteResponse addFile(
            @ApiParam(value = "文件", required = true)
            @RequestPart("file") MultipartFile file,

            @ApiParam(value = "单条记录(JSON 字符串)", required = true)
            @RequestPart("acc") String accJson
    ) {
        // JSON → 对象
        return service.addFile(file, JSON.parseObject(accJson, AccessoriesPurchaseContract.class));
    }

    @PostMapping(value = "/updateFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("单条记录修改文件")
    public WhiteResponse updateFile(
            @ApiParam(value = "文件", required = true)
            @RequestPart("file") MultipartFile file,

            @ApiParam(value = "单条记录(JSON 字符串)", required = true)
            @RequestPart("acc") String accJson
    ) {
        return service.updateFile(file, JSON.parseObject(accJson, AccessoriesPurchaseContract.class));
    }

    // 删除记录，将会一并删除其包含的文件
    @Override
    @DeleteMapping("/{id}")
    @ApiOperation("单条记录删除")
    public WhiteResponse delete(
            @ApiParam(value = "单条记录的id", required = true)
            @PathVariable Long id
    ) {
        return service.deleteFile(id);
    }

    // 导入excel表格
    @PostMapping(value = "/importExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("从 Excel 导入辅料购货合同数据")
    public WhiteResponse importExcel(
            @ApiParam(value = "Excel 文件", required = true)
            @RequestPart("file") MultipartFile file,
            @ApiParam(value = "导入的标识", required = true)
            @RequestPart("importId") String importId,
            @ApiParam(value = "客户id")
            @RequestPart(required = false) String guestId
    ) {
        return service.importExcel(file, Long.parseLong(importId), guestId != null ? Integer.parseInt(guestId) : null);
    }


    // 导出excel表格
    @PostMapping("/exportExcel")
    @ApiOperation("导出辅料购货合同数据到 Excel")
    public void exportExcel(
            @RequestBody Map<String, Object> body,
            HttpServletResponse response) {

        service.exportExcel(body, response);
    }

    // 重写分页查询
    @GetMapping("/pageByGuestId")
    @ApiOperation("分页查询")
    public WhiteResponse page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) Long importId
    ) {
        return service.getPageByUserRole(current, size, importId, guestId);
    }

    @GetMapping("/getAccListByImportId/{importId}")
    @ApiOperation("分页查询")
    public WhiteResponse pageByImportId(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @PathVariable Long importId
    ) {
        return service.getPageByUserRole(current, size, importId, null);
    }



}