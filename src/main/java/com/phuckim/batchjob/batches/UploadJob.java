package com.phuckim.batchjob.batches;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HIVELAB
 */
@Configuration
@RequiredArgsConstructor
public class UploadJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final JobExecutionListenerSupport listenerSupport;
    private final Step handleChunkUpload;

    @Bean
    public Job handleOutsourceJob() {
        return jobBuilderFactory.get("handleUploadJob")
                .incrementer(new RunIdIncrementer())
                .listener(listenerSupport)
                .start(handleChunkUpload)
                .build();
    }
}
