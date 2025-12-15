package white.rs.controller;

import com.alibaba.fastjson.JSON;
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
            @RequestPart("file") MultipartFile file
    ) {
        return service.importExcel(file);
    }

}

