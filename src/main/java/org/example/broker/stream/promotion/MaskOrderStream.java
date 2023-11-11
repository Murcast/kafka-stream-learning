package org.example.broker.stream.promotion;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.example.message.OrderMessage;
import org.example.util.CommodityStreamUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class MaskOrderStream {

    @Bean
    public KStream<String, OrderMessage> maskOrderStreamBean(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderMessageSerde = new JsonSerde<>(OrderMessage.class);
        var stream = builder.stream(
                "t-commodity-order",
                Consumed.with(stringSerde, orderMessageSerde)
        ).mapValues(CommodityStreamUtil::maskCreditCard);

        stream.to("t-commodity-order-masked", Produced.with(stringSerde, orderMessageSerde));

        stream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked kafka stream message"));

        return stream;
    }
}
