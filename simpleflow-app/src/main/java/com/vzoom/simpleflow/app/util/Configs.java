package com.vzoom.simpleflow.app.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Configs {

    public static final String PROCESS_CONFIG_FOLDER = "/simpleflow";

    public static <T> T getProcessConfig(String fileName, TypeReference<T> typeReference) {
        try (InputStream resourceAsStream = Configs.class.getResourceAsStream(PROCESS_CONFIG_FOLDER + '/' + fileName + ".json")) {
            String str = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8.name());
            ObjectMapper bean = SpringBeans.getBean(ObjectMapper.class);
            return bean.readValue(str, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
