package org.example.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

public class CustomJsonSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public byte[] serialize(String topic, T data) {
        return objectMapper.writeValueAsBytes(data);
    }
}
