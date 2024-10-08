package com.phuckim.batchjob.batches.custom.writers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.util.List;

/**
 * @author HIVELAB
 */
@Slf4j
public class UploadWriter implements ItemWriter<List<File>> {

    @Override
    public void write(List<? extends List<File>> list) throws Exception {
        log.info("Upload success: " + list.size());
    }
}
