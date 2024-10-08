package com.phuckim.batchjob.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author HIVELAB
 */
public interface S3UploadService {
    List<File> uploadList(List<MultipartFile> files) throws InterruptedException, IOException;

    void upload(MultipartFile file, String key) throws InterruptedException, IOException;
}
