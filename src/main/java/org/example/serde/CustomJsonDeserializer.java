package org.example.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomJsonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> targetClass;

    public CustomJsonDeserializer(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    @SneakyThrows
    public T deserialize(String topic, byte[] data) {
        return objectMapper.readValue(data, targetClass);
    }
}
