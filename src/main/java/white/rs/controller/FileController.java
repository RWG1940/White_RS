package white.rs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.controller.base.BaseController;
import white.rs.domain.FileResource;
import white.rs.domain.FileShareRequest;
import white.rs.service.FileResourceService;
import white.rs.service.MinioService;

import java.util.List;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/api/files")
public class FileController extends BaseController<FileResource, FileResourceService> {

    @Autowired
    private MinioService minioService;
    protected final FileResourceService service;
    protected FileController(FileResourceService service) {
        super(service);
        this.service = service;
    }

    @ApiOperation("上传文件 with minio")
    @PostMapping("/upload")
    public WhiteResponse uploadFile(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "业务类型") @RequestParam(required = false) String bizType,
            @ApiParam(value = "业务ID") @RequestParam(required = false) Long bizId,
            @ApiParam(value = "创建者ID") @RequestParam(required = false) Long creatorId) {
        return minioService.uploadFile(file, bizType, bizId, creatorId);
    }

    @ApiOperation("下载文件 with minio")
    @GetMapping("/download/{fileKey}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileKey) {
        return minioService.downloadFile(fileKey);
    }


    @ApiOperation("删除文件 with minio")
    @DeleteMapping("/delete")
    public WhiteResponse deleteFiles(@RequestBody List<String> fileKeys) {
        return minioService.deleteFile(fileKeys.toArray(new String[0]));
    }

    @ApiOperation("更新文件 with minio")
    @PostMapping("/update/{fileKey}")
    public WhiteResponse updateFile(
            @PathVariable String fileKey,
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file) {
        return minioService.updateFile(fileKey, file);
    }

    @ApiOperation("按照条件获取分页数据")
    @GetMapping("/pageByBiz")
    public WhiteResponse pageByBiz(
            @ApiParam(value = "页码", required = true) @RequestParam Integer pageNum,
            @ApiParam(value = "每页数量", required = true) @RequestParam Integer pageSize,
            @ApiParam(value = "业务类型") @RequestParam(required = false) String bizType,
            @ApiParam(value = "业务ID") @RequestParam(required = false) Long bizId) {
        return service.pageByBiz(pageNum, pageSize, bizType, bizId);
    }

    @ApiOperation("获取所有文件大小总和")
    @GetMapping("/getTotalSize")
    public WhiteResponse getTotalSize() {
        return service.getTotalSize();
    }

    @ApiOperation("创建文件分享")
    @PostMapping("/createShare")
    public WhiteResponse createShare(
            @RequestBody FileShareRequest request
    ) {
        return service.createShare( request.getSharedWithUserId(), request.getSharePassword(), request.getShareType(), request.getExpirationTime(), request.getFileIds());
    }

    @ApiOperation("获取文件分享")
    @GetMapping("/getShare/{shareLink}")
    public WhiteResponse getShare(
            @ApiParam(value = "分享链接", required = true)
            @PathVariable String shareLink
    ) {
        return service.getShare(shareLink);
    }

}