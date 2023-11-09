package org.example.broker.stream.promotion;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PromotionUppercaseStream {

    @Bean
    public KStream<String, String> promotionUppercaseStreamBean(StreamsBuilder builder) {
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), Serdes.String()));
        var resultStream = sourceStream.mapValues(str -> str.toUpperCase());

        resultStream.to("t-commodity-promotion-uppercase");

        sourceStream.print(Printed.toSysOut());
        resultStream.print(Printed.toSysOut());

        return resultStream;
    }
}
