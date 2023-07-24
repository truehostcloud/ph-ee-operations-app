package org.apache.fineract.operations;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class to read a JSON string from application.yml and convert it to a list of ams.
 */
@Configuration
@Slf4j
public class AMSConfig {

    @Value("${ams.sources}")
    private String amsSourcesString;

    /**
     * Gets the list of ams from the JSON string in application.yml.
     *
     * @return The list of items parsed from the JSON string.
     * @throws RuntimeException if there is an error while reading or parsing the JSON string.
     */
    public List<AmsSource> getAmsSourcesList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(amsSourcesString, new TypeReference<List<AmsSource>>() {
            });
        } catch (IOException e) {
            log.error("Failed to read JSON list from application.yml", e);
        }
        return new ArrayList<>();
    }

    /**
     * Represents an item in the JSON list.
     */
    public static class AmsSource {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

    }
}
