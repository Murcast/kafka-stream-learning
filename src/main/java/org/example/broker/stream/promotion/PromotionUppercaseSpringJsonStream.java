package org.example.broker.stream.promotion;

import org.example.message.PromotionMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Slf4j
//@Configuration
public class PromotionUppercaseSpringJsonStream {

    @Bean
    public KStream<String, PromotionMessage> promotionUppercaseSpringJsonStreamBean(StreamsBuilder builder) {
        var string = Serdes.String();
        var promotionMessageSerde = new JsonSerde<>(PromotionMessage.class);
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(string, promotionMessageSerde));
        var resultStream = sourceStream.mapValues(this::uppercasePromotionCode);

        resultStream.to("t-commodity-promotion-uppercase", Produced.with(string, promotionMessageSerde));

        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("JSON serde original stream"));
        resultStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("JSON serde uppercase stream"));

        return resultStream;
    }

    @SneakyThrows
    private PromotionMessage uppercasePromotionCode(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
