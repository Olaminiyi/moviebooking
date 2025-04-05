package com.moviebooking.moviebooking.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;

public final class MovieJsonData {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static  final Logger log = LoggerFactory.getLogger(MovieJsonData.class);

    public  static <T> List<T> readFile(String path, TypeReference<List<T>> object){
        try {
            File file = new File(path);
            return objectMapper.readValue(file, object);

        }
        catch (Exception  e){
            log.error("failed to read file from: " + path);
            return Collections.emptyList();
        }
    }
}
