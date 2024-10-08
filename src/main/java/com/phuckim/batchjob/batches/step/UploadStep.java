package com.phuckim.batchjob.batches.step;


import com.phuckim.batchjob.batches.custom.processors.UploadJobProcessor;
import com.phuckim.batchjob.batches.custom.readers.UploadReader;
import com.phuckim.batchjob.batches.custom.writers.UploadWriter;
import com.phuckim.batchjob.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UploadStep {

    private final S3UploadService s3UploadService;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @Qualifier("handleChunkUpload")
    public Step handleChunkUpload(ItemReader<List<MultipartFile>> reader) {
        return stepBuilderFactory
                .get("stepHandleUpload")
                .<List<MultipartFile>, List<File>>chunk(10)
                .reader(reader)
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<List<MultipartFile>> reader(List<MultipartFile> files) {
        return new UploadReader(files);
    }

    @Bean
    @StepScope
    public ItemProcessor<List<MultipartFile>, List<File>> processor() {
        return new UploadJobProcessor(s3UploadService);
    }

    @Bean
    @StepScope
    public ItemWriter<List<File>> writer() {
        return new UploadWriter();
    }
}
