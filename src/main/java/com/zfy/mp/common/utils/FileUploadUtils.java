package com.zfy.mp.common.utils;

import cn.hutool.poi.excel.ExcelWriter;
import com.zfy.mp.common.constants.Const;
import com.zfy.mp.common.constants.Constants;
import com.zfy.mp.enums.UploadEnum;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

import static com.google.common.io.Files.getFileExtension;

/**
 *
 * @文件名: FileUploadUtils.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.utils
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-16 15:21
 * @版本号: V2.4.0
 */
@Log4j2
@Component
public class FileUploadUtils {
    @Resource
    private MinioClient minioClient;
    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * 上传文件
     *
     * @param uploadEnum 文件枚举
     * @param file       文件
     * @return 上传后的文件地址
     * @throws Exception 异常
     */
    public String upload(UploadEnum uploadEnum, MultipartFile file) throws Exception {
        isCheck(uploadEnum, file);
        if (isFormatFile(file.getOriginalFilename(), uploadEnum.getFormat())) {
            InputStream st = file.getInputStream();
            String name = UUID.randomUUID().toString();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .headers(Map.of(Const.CONTENT_TYPE, Objects.requireNonNull(file.getContentType())))
                    .object(uploadEnum.getDir() + name + "." + getFileExtension(file.getOriginalFilename()))
                    .stream(st, file.getSize(), -1)
                    .build();
            minioClient.putObject(args);
            return endpoint + "/" + bucketName + "/" + uploadEnum.getDir() + name + "." + getFileExtension(file.getOriginalFilename());

        }
        log.error("--------------------上传文件格式不正确--------------------");
        throw new FileUploadException("上传文件类型错误");
    }

    public String uploadStream(UploadEnum uploadEnum, InputStream  inputStream, String filename, String contentType, Long size) throws Exception {
        // Validate parameters
        if (inputStream == null || filename == null || contentType == null) {
            throw new IllegalArgumentException("InputStream, filename, and contentType cannot be null");
        }

        // Check if the file format is allowed
        if (!isFormatFile(filename, uploadEnum.getFormat())) {
            log.error("--------------------上传文件格式不正确--------------------");
            throw new FileUploadException("上传文件类型错误");
        }

        // Generate a unique name for the file
        String name = UUID.randomUUID().toString();
        String objectName = uploadEnum.getDir() + name + "." + getFileExtension(filename);

        // Build the upload arguments
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .headers(Map.of(Const.CONTENT_TYPE, contentType))
                .object(objectName)
                .stream(inputStream, size, -1)
                .build();

        // Upload the file
        minioClient.putObject(args);

        // Return the URL of the uploaded file
        return endpoint + "/" + bucketName + "/" + objectName;


    }


    /**
     * 使用 Hutool 的 ExcelWriter 生成 Excel 并直接上传到 MinIO
     * 内部使用 PipedInputStream/PipedOutputStream 进行流转换
     *
     * @param uploadEnum  上传配置枚举
     * @param filename    目标文件名
     * @param contentType 文件类型
     * @param size        预估文件大小（字节，如果无法预估可传 -1 或 5MB * 1024 * 1024）
     * @param writer      写入流的逻辑函数
     * @return            文件访问的完整 URL
     * @throws Exception  上传过程中发生的异常
     */
    public String uploadWithOutputStream(UploadEnum uploadEnum, String filename, String contentType, long size, ExcelWriter writer) throws Exception {
        // 1. 校验参数
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("文件名 filename 不能为空");
        }
        if (writer == null) {
            throw new IllegalArgumentException("写入逻辑 writer 不能为空");
        }

        // 2. 校验文件格式
        if (!isFormatFile(filename, uploadEnum.getFormat())) {
            log.error("--------------------上传文件格式不正确--------------------");
            throw new FileUploadException("上传文件类型错误");
        }

        // 3. 创建管道流
        // 注意：PipedInputStream 需要设置缓冲区大小，默认通常较小，大文件建议调大
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        // 4. 生成唯一文件名和对象路径
        String newFileName = UUID.randomUUID() + "." + getFileExtension(filename);
        String objectName = uploadEnum.getDir() + newFileName;

        // 5. 使用异步线程执行写入操作，防止阻塞主线程导致死锁
        CompletableFuture<Void> writeFuture = CompletableFuture.runAsync(() -> {
            try (OutputStream os = out) { // 使用 try-with-resources 确保流关闭
                writer.flush(os);
            } catch (IOException e) {
                throw new CompletionException("写入流出错", e);
            } finally {
                writer.close();
            }
        });

        try {

            // 6. 构建 MinIO 上传参数
            PutObjectArgs args = PutObjectArgs.builder()
                    .headers(Map.of(Const.CONTENT_TYPE, contentType))
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(in, size, -1)
                    .build();

            // 7. 执行上传
            minioClient.putObject(args);

            // 8. 返回访问地址
            return endpoint + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            // 如果上传失败，取消写入任务
            writeFuture.cancel(true);
            throw e;
        } finally {
            // 确保写入任务完成（无论成功或失败）
            writeFuture.join();
        }
    }

    private boolean isFormatFile(String originalFilename, List<String> format) {
        for (String s : format) {
            if (originalFilename.endsWith(s))
                return true;
        }
        return false;
    }

    private void isCheck(UploadEnum uploadEnum, MultipartFile file) throws FileUploadException {
        if (file.isEmpty()) {
            throw new FileUploadException("上传文件为空");
        }
        // 验证文件大小
        if (verifyTheFileSize(file.getSize(), uploadEnum.getLimitSize())) {
            throw new FileUploadException("上传文件超过限制大小:" + uploadEnum.getLimitSize() + "MB");
        }
    }

    public Boolean verifyTheFileSize(Long fileSize, Double limitSize) {
        // 转为相同大小格式
        double formatFileSize = convertFileSizeToMB(fileSize);
        if (formatFileSize < limitSize) {
            return false;
        }
        return true;
    }

    /**
     * B 转 MB
     *
     * @param sizeInBytes 文件大小 B
     * @return 文件大小 MB
     */
    public double convertFileSizeToMB(long sizeInBytes) {
        double sizeInMB = (double) sizeInBytes / (1024 * 1024);
        String formatted = String.format("%.2f", sizeInMB);
        // String转为Long
        return Double.parseDouble(formatted);
    }
}
