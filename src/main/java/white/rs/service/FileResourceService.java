package white.rs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.FileResource;

import java.util.List;

/**
* @author Administrator
* @description 针对表【file_resource(对象存储文件元数据表)】的数据库操作Service
* @createDate 2025-12-09 09:09:41
*/
public interface FileResourceService extends IService<FileResource> {
    
    /**
     * 保存文件信息到数据库
     * @param file MultipartFile文件对象
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @param creatorId 创建者ID
     * @return 保存的文件信息
     */
    FileResource saveFileInfo(MultipartFile file, String bizType, Long bizId, Long creatorId);
    
    /**
     * 保存文件信息到数据库（包含fileKey）
     * @param file MultipartFile文件对象
     * @param fileKey 文件key
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @param creatorId 创建者ID
     * @return 保存的文件信息
     */
    FileResource saveFileInfoWithKey(MultipartFile file, String fileKey, String bizType, Long bizId, Long creatorId);
    
    /**
     * 根据文件key获取文件信息
     * @param fileKey 文件key
     * @return 文件信息
     */
    FileResource getByFileKey(String fileKey);
    
    /**
     * 更新文件信息
     * @param fileResource 文件信息实体
     * @return 是否更新成功
     */
    boolean updateFileInfo(FileResource fileResource);
    
    /**
     * 删除文件信息（逻辑删除）
     * @param fileKey 文件key
     * @return 是否删除成功
     */
    boolean deleteFileInfo(String fileKey);

    WhiteResponse pageByBiz(Integer pageNum, Integer pageSize, String bizType, Long bizId);

    WhiteResponse getTotalSize();

    WhiteResponse createShare(Long sharedWithUserId, String sharePassword, String shareType, String expirationTime, List<Long> file_ids);

    WhiteResponse getShare(String shareLink);
}
