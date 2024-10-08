package com.phuckim.batchjob.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.phuckim.batchjob.service.S3UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author HIVELAB
 */
@Service
@Slf4j
public class S3UploadServiceImpl implements S3UploadService {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.endpoint}")
    private String awsDomain;

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private AmazonS3 s3Client;

    @Override
    public List<File> uploadList(List<MultipartFile> multipartFiles) throws InterruptedException, IOException {
        if (multipartFiles.isEmpty()) {
            return null;
        }

        String commonPath = "phuc";
        File baseDir = new File(commonPath);

        if (!baseDir.exists() && !baseDir.mkdirs()) {
            throw new IOException("Failed to create directory: " + commonPath);
        }

        List<File> files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(multipartFile.getBytes());
            }
            files.add(file);
        }

        var upload = transferManager.uploadFileList(bucketName, commonPath, baseDir, files);
        upload.waitForCompletion();

        for (File file : files) {
            if (!file.delete()) {
                log.error("Failed to delete file: " + file.getAbsolutePath());
            }
        }

        if (baseDir.isDirectory() && Objects.requireNonNull(baseDir.list()).length == 0) {
            if (!baseDir.delete()) {
                log.error("Failed to delete directory: " + baseDir.getAbsolutePath());
            }
        }

        return files;
    }

    @Override
    public void upload(MultipartFile multipartFile, String key) throws InterruptedException, IOException {
        var file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (var fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }

        // TransferManager processes all transfers asynchronously,
        // so this call returns immediately.
        var upload = transferManager.upload(bucketName, key + "/" + multipartFile.getOriginalFilename(), file);
        log.debug("Object upload started");
        // Optionally, wait for the upload to finish before continuing.
        upload.waitForCompletion();
        Files.deleteIfExists(file.toPath());

        log.debug("Object upload complete");
    }
}
