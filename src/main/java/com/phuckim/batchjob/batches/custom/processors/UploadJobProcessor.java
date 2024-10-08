package com.phuckim.batchjob.batches.custom.processors;

import com.phuckim.batchjob.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UploadJobProcessor implements ItemProcessor<List<MultipartFile>, List<File>> {

    private final S3UploadService s3UploadService;

    @Override
    public List<File> process(List<MultipartFile> multipartFiles) throws Exception {
        return s3UploadService.uploadList(multipartFiles);
    }
}
