package com.moviebooking.moviebooking.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
public final class MovieJasonData {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public  static <T> List<T> readFile(String path, TypeReference<List<T>> object){
        try {
            File file = new File(path);
            return objectMapper.readValue(file, object);

        }
        catch (Exception  e){
            log.info("failed to read file from: " + path);
            return Collections.emptyList();
        }
    }
}
