package org.example.broker.stream.promotion;

import org.example.message.PromotionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;

@Slf4j
//@Configuration
public class PromotionUppercaseJsonStream {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KStream<String, String> promotionUppercaseJsonStreamBean(StreamsBuilder builder) {
        var string = Serdes.String();
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(string, string));
        var resultStream = sourceStream.mapValues(this::uppercasePromotionCode);

        resultStream.to("t-commodity-promotion-uppercase");

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("JSON original stream"));
        resultStream.print(Printed.<String, String>toSysOut().withLabel("JSON uppercase stream"));

        return resultStream;
    }

    @SneakyThrows
    private String uppercasePromotionCode(String message) {
        var promotionMessage = objectMapper.readValue(message, PromotionMessage.class);
        return objectMapper.writeValueAsString(new PromotionMessage(promotionMessage.getPromotionCode().toUpperCase()));
    }
}
