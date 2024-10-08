package com.phuckim.batchjob.batches.custom.readers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UploadReader implements ItemReader<List<MultipartFile>> {

    private final List<MultipartFile> multipartFiles;

    @Override
    public List<MultipartFile> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return multipartFiles;
    }
}
