package white.rs.service;

import io.minio.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.FileResource;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinioService {

    /**
     * 上传文件到MinIO（完整业务逻辑）
     *
     * @param file 文件
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @param creatorId 创建者ID
     * @return 文件在MinIO中的名称
     */
    WhiteResponse uploadFile(MultipartFile file, String bizType, Long bizId, Long creatorId);

    /**
     * 从MinIO下载文件（完整业务逻辑）
     *
     * @param fileKey 文件key
     * @return 文件下载响应
     */
    ResponseEntity<byte[]> downloadFile(String fileKey);

    /**
     * 从MinIO删除文件（完整业务逻辑）
     * 支持单个或多个文件删除
     *
     * @param fileKeys 文件key或文件keys列表
     * @return 删除结果
     */
    WhiteResponse deleteFile(String... fileKeys);

    /**
     * 更新MinIO中的文件（完整业务逻辑）
     *
     * @param fileKey 文件key
     * @param file 文件
     * @return 更新结果
     */
    WhiteResponse updateFile(String fileKey, MultipartFile file);

    /**
     * 下载文件并返回字节数组
     *
     * @param fileKey 文件key
     * @return 文件字节数组
     */
    byte[] downloadFileAsBytes(String fileKey) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    // 保留原有的底层方法供内部使用
    String uploadFileToMinio(MultipartFile file, FileResource fileResource) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    InputStream getFile(String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    void deleteFileFromMinio(String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    String updateFileInMinio(String fileName, MultipartFile file, FileResource fileResource) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}