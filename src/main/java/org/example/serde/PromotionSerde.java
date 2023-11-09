package org.example.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.example.message.PromotionMessage;

public class PromotionSerde extends CustomSerde<PromotionMessage> {

    public PromotionSerde() {
        super(new CustomJsonSerializer<>(), new CustomJsonDeserializer<>(PromotionMessage.class));
    }
}
