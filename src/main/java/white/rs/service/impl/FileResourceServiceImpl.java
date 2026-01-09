package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.FileResource;
import white.rs.domain.FileSharing;
import white.rs.domain.FileSharingFiles;
import white.rs.mapper.FileResourceMapper;
import white.rs.service.FileResourceService;
import white.rs.service.FileSharingFilesService;
import white.rs.service.FileSharingService;
import white.rs.service.UsersService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author Administrator
* @description 针对表【file_resource(对象存储文件元数据表)】的数据库操作Service实现
* @createDate 2025-12-09 09:09:41
*/
@Service
public class FileResourceServiceImpl extends ServiceImpl<FileResourceMapper, FileResource>
    implements FileResourceService{

    @Value("${minio.bucket}")
    private String bucketName;
    
    @Autowired
    private FileSharingService fileSharingService;
    
    @Autowired
    private FileSharingFilesService fileSharingFilesService;
    
    @Autowired
    private UsersService usersService;

    @Override
    public FileResource saveFileInfo(MultipartFile file, String bizType, Long bizId, Long creatorId) {
        FileResource fileResource = new FileResource();
        fileResource.setFileName(file.getOriginalFilename());
        fileResource.setFileSize(file.getSize());
        fileResource.setFileType(file.getContentType());
        
        // 设置文件扩展名
        if (file.getOriginalFilename() != null && file.getOriginalFilename().lastIndexOf(".") > 0) {
            fileResource.setFileExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        }
        
        fileResource.setBizType(bizType);
        fileResource.setBizId(bizId);
        fileResource.setCreatorId(creatorId);
        fileResource.setCreateTime(new Date());
        fileResource.setUpdateTime(new Date());
        fileResource.setIsDeleted(0); // 未删除
        
        this.save(fileResource);
        return fileResource;
    }
    
    @Override
    public FileResource saveFileInfoWithKey(MultipartFile file, String fileKey, String bizType, Long bizId, Long creatorId) {
        FileResource fileResource = new FileResource();
        fileResource.setFileName(file.getOriginalFilename());
        fileResource.setFileKey(fileKey);
        fileResource.setFileUrl(generateFileUrl(fileKey)); // 生成文件访问URL
        fileResource.setFileSize(file.getSize());
        fileResource.setFileType(file.getContentType().length() > 30 ? file.getContentType().substring(0, 30) : file.getContentType());
        fileResource.setBucket(bucketName); // 设置bucket
        
        // 设置文件扩展名
        if (file.getOriginalFilename() != null && file.getOriginalFilename().lastIndexOf(".") > 0) {
            fileResource.setFileExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        }
        
        fileResource.setBizType(bizType);
        fileResource.setBizId(bizId);
        fileResource.setCreatorId(creatorId);
        fileResource.setCreateTime(new Date());
        fileResource.setUpdateTime(new Date());
        fileResource.setIsDeleted(0); // 未删除
        
        this.save(fileResource);
        return fileResource;
    }
    
    /**
     * 生成文件访问URL
     * 这里暂时使用MinIO的默认格式，实际项目中可能需要配置访问域名
     */
    private String generateFileUrl(String fileKey) {
        // 这里可以根据实际需求生成文件访问URL
        // 例如：http://minio-server:9000/bucket/fileKey
        // 或者使用反向代理的URL：https://your-domain.com/files/fileKey
        return "/api/files/download/" + fileKey; // 使用当前系统的下载接口作为URL
    }

    @Override
    public FileResource getByFileKey(String fileKey) {
        QueryWrapper<FileResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_key", fileKey);
        queryWrapper.eq("is_deleted", 0); // 未删除的文件
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateFileInfo(FileResource fileResource) {
        fileResource.setUpdateTime(new Date());
        return this.updateById(fileResource);
    }

    @Override
    public boolean deleteFileInfo(String fileKey) {
        FileResource fileResource = this.getByFileKey(fileKey);
        if (fileResource != null) {
            fileResource.setIsDeleted(1); // 标记为已删除
            fileResource.setUpdateTime(new Date());
            return this.updateById(fileResource);
        }
        return false;
    }

    @Override
    public WhiteResponse pageByBiz(Integer pageNum, Integer pageSize, String bizType, Long bizId) {
         // 创建分页对象
        Page<FileResource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<FileResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("biz_type", bizType);
        queryWrapper.eq("biz_id", bizId);
        queryWrapper.eq("is_deleted", 0);
        return WhiteResponse.success(this.page(page, queryWrapper));
    }

    @Override
    public WhiteResponse getTotalSize() {
        // 获取所有未删除的文件
        QueryWrapper<FileResource> queryWrapper = new QueryWrapper<FileResource>().eq("is_deleted", 0);
        // 进行计算
        return WhiteResponse.success(this.list(queryWrapper).stream().mapToLong(FileResource::getFileSize).sum());
    }

    @Override
    public WhiteResponse createShare(Long sharedWithUserId, String sharePassword, String shareType, String expirationTime, List<Long> file_ids) {
        // 1.获取当前登录用户的ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return WhiteResponse.fail("用户未登录");
        }

        // 从JWT token中获取用户名，然后查询用户ID
        String currentUsername = authentication.getName();
        white.rs.domain.Users currentUser = usersService.getByUsername(currentUsername);
        if (currentUser == null) {
            return WhiteResponse.fail("当前用户信息不存在");
        }
        Long currentUserId = currentUser.getId();
        
        // 2.生成一个随机的shareId
        // 这里使用UUID生成唯一分享ID
        String uniqueShareId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        
        // 3.解析过期时间
        Date expirationDate = null;
        if (expirationTime != null && !expirationTime.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                expirationDate = sdf.parse(expirationTime);
            } catch (ParseException e) {
                return WhiteResponse.fail("过期时间格式错误，应为：yyyy-MM-dd HH:mm:ss");
            }
        }
        
        // 4.构建FileSharing对象并保存
        FileSharing fileSharing = new FileSharing();
        fileSharing.setOwnerId(currentUserId); // 分享者ID
        fileSharing.setSharedWithUserId(sharedWithUserId); // 被分享用户ID
        fileSharing.setShareLink(uniqueShareId); // 生成分享链接
        fileSharing.setSharePassword(sharePassword); // 分享密码
        fileSharing.setShareType(shareType); // 分享类型
        fileSharing.setShareTime(new Date()); // 分享时间
        fileSharing.setExpirationTime(expirationDate); // 过期时间
        
        // 保存分享信息
        boolean saved = fileSharingService.save(fileSharing);
        if (!saved) {
            return WhiteResponse.fail("创建分享失败");
        }
        
        // 获取保存后的分享ID（自增主键）
        Integer savedShareId = fileSharing.getShareId();
        // 5.创建文件ID与分享ID的关联关系
        if (file_ids != null && !file_ids.isEmpty()) {
            for (Long fileId : file_ids) {
                FileSharingFiles fileSharingFiles = new FileSharingFiles();
                fileSharingFiles.setShareId(savedShareId); // 使用数据库生成的分享ID
                fileSharingFiles.setFileId(fileId);
                fileSharingFilesService.save(fileSharingFiles);
            }
        }
        
        return WhiteResponse.success(fileSharing.getShareLink());
    }

    @Override
    public WhiteResponse getShare(String shareLink) {
        // 1.通过shareLink查询分享信息id
        QueryWrapper<FileSharing> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("share_link", shareLink);
        FileSharing fileSharing = fileSharingService.getOne(queryWrapper);
        if (fileSharing == null) {
            return WhiteResponse.fail("分享信息不存在");
        }
        Integer shareId = fileSharing.getShareId();
        // 2.根据分享信息id查询分享文件id
        QueryWrapper<FileSharingFiles> queryWrapperFiles = new QueryWrapper<>();
        queryWrapperFiles.eq("share_id", shareId);
        List<FileSharingFiles> fileSharingFilesList = fileSharingFilesService.list(queryWrapperFiles);
        // 3.根据分享文件id查询文件信息
        List<FileResource> fileResourceList = new ArrayList<>();
        for (FileSharingFiles fileSharingFiles : fileSharingFilesList) {
            Long fileId = fileSharingFiles.getFileId();
            FileResource fileResource = this.getById(fileId);
            fileResourceList.add(fileResource);
        }
        // 4.返回文件信息列表和分享信息
        HashMap<String, Object> resultData = new HashMap<>();
        resultData.put("fileSharing", fileSharing);
        resultData.put("fileResourceList", fileResourceList);
        return WhiteResponse.success(resultData);
    }


}


