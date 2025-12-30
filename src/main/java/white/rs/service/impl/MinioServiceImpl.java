package white.rs.service.impl;

import io.minio.*;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import white.rs.common.response.WhiteResponse;
import white.rs.domain.FileResource;
import white.rs.service.FileResourceService;
import white.rs.service.MinioService;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FileResourceService fileResourceService;

    @Value("${minio.bucket}")
    private String bucketName;

    @PostConstruct
    public void initialize() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //  创建Bucket，如果不存在
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @Override
    public WhiteResponse uploadFile(MultipartFile file, String bizType, Long bizId, Long creatorId) {
        try {
            if (file.isEmpty()) {
                return WhiteResponse.fail("文件不能为空");
            }

            // 先生成唯一的文件key
            String fileKey = generateFileKey(file);

            // 保存文件信息到数据库（包含fileKey）
            fileResourceService.saveFileInfoWithKey(file, fileKey, bizType, bizId, creatorId);

            // 上传文件到MinIO
            uploadFileToMinioWithKey(fileKey, file);

            logger.info("文件上传成功，文件名：{}，文件key：{}", file.getOriginalFilename(), fileKey);

            return WhiteResponse.success("/api/files/download/" + fileKey);
        } catch (Exception e) {
            logger.error("文件上传失败", e);
            return WhiteResponse.fail("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 生成唯一的文件key
     */
    private String generateFileKey(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }

    /**
     * 上传文件到MinIO（使用指定的文件key）
     */
    private void uploadFileToMinioWithKey(String fileKey, MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileKey)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String fileKey) {
        try (InputStream inputStream = getFile(fileKey)) {
            // 获取文件信息
            FileResource fileResource = fileResourceService.getByFileKey(fileKey);
            String fileName = fileResource != null ? fileResource.getFileName() : fileKey;

            // 使用传统的读取方式替代readAllBytes()
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            byte[] bytes = buffer.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            try {
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            } catch (UnsupportedEncodingException e) {
                // 如果UTF-8编码不支持，则使用默认编码
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"");
            }

            logger.info("文件下载成功，文件key：{}", fileKey);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("文件下载失败，文件key：{}", fileKey, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Override
    public WhiteResponse deleteFile(String... fileKeys) {
        // Convert varargs to list
        List<String> fileKeyList = Arrays.asList(fileKeys);

        List<String> failedDeletes = new ArrayList<>();
        List<String> successfulDeletes = new ArrayList<>();

        for (String fileKey : fileKeyList) {
            try {
                // 从MinIO删除文件
                deleteFileFromMinio(fileKey);

                // 从数据库中标记为已删除
                boolean deleted = fileResourceService.deleteFileInfo(fileKey);

                if (deleted) {
                    successfulDeletes.add(fileKey);
                    logger.info("文件删除成功，文件key：{}", fileKey);
                } else {
                    failedDeletes.add(fileKey);
                    logger.warn("文件删除失败，文件key：{} 不存在", fileKey);
                }
            } catch (Exception e) {
                failedDeletes.add(fileKey);
                logger.error("文件删除失败，文件key：{}", fileKey, e);
            }
        }

        if (failedDeletes.isEmpty()) {
            if (fileKeyList.size() == 1) {
                return WhiteResponse.success("文件删除成功");
            } else {
                return WhiteResponse.success("所有文件删除成功，共删除" + successfulDeletes.size() + "个文件");
            }
        } else {
            if (fileKeyList.size() == 1) {
                return WhiteResponse.fail("文件删除失败");
            } else {
                return WhiteResponse.success("部分文件删除成功，成功" + successfulDeletes.size() + "个，失败" + failedDeletes.size() + "个");
            }
        }
    }

    @Override
    public WhiteResponse updateFile(String fileKey, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return WhiteResponse.fail("文件不能为空");
            }

            // 获取原有文件信息
            FileResource fileResource = fileResourceService.getByFileKey(fileKey);
            if (fileResource == null) {
                return WhiteResponse.fail("文件不存在");
            }

            // 更新文件到MinIO
            String updatedFileKey = updateFileInMinio(fileKey, file, fileResource);

            // 更新文件信息
            fileResourceService.updateFileInfo(fileResource);

            logger.info("文件更新成功，文件key：{}", updatedFileKey);

            return WhiteResponse.success(updatedFileKey);
        } catch (Exception e) {
            logger.error("文件更新失败，文件key：{}", fileKey, e);
            return WhiteResponse.fail("文件更新失败：" + e.getMessage());
        }
    }

    // 底层MinIO操作方法
    @Override
    public String uploadFileToMinio(MultipartFile file, FileResource fileResource) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileName;
        // 如果提供了fileResource且已有fileKey，则使用现有fileKey，否则生成新的
        if (fileResource != null && fileResource.getFileKey() != null && !fileResource.getFileKey().isEmpty()) {
            fileName = fileResource.getFileKey();
        } else {
            // 生成唯一文件名
            fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        }

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        return fileName;
    }

    @Override
    public byte[] downloadFileAsBytes(String fileKey) {
        try (InputStream inputStream = getFile(fileKey)) {
            // 使用ByteArrayOutputStream读取输入流
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            byte[] result = buffer.toByteArray();
            return result;
        } catch (Exception e) {
            logger.error("文件下载失败，文件key：{}", fileKey, e);
            return null;
        }
    }

    @Override
    public InputStream getFile(String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }

    @Override
    public void deleteFileFromMinio(String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }

    @Override
    public String updateFileInMinio(String fileName, MultipartFile file, FileResource fileResource) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 直接使用给定的文件名更新文件
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        // 更新文件元数据
        if (fileResource != null) {
            fileResource.setFileName(file.getOriginalFilename());
            fileResource.setFileSize(file.getSize());
            fileResource.setFileType(file.getContentType());
            if (file.getOriginalFilename() != null && file.getOriginalFilename().lastIndexOf(".") > 0) {
                fileResource.setFileExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            }
            fileResource.setBucket(bucketName);
        }

        return fileName;
    }


}