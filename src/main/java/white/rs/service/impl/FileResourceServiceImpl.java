package white.rs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import white.rs.domain.FileResource;
import white.rs.mapper.FileResourceMapper;
import white.rs.service.FileResourceService;

import java.util.Date;

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
        fileResource.setFileType(file.getContentType());
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
}




