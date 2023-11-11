package org.example.broker.stream.promotion;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.example.message.OrderMessage;
import org.example.message.OrderPatternMessage;
import org.example.message.OrderRewardMessage;
import org.example.util.CommodityStreamUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class OrderOneStream {

    @Bean
    public KStream<String, OrderMessage> kafkaStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderMessageJsonSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternMessageJsonSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardMessageJsonSerde = new JsonSerde<>(OrderRewardMessage.class);
        var stream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderMessageJsonSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        stream
                .mapValues(CommodityStreamUtil::convertToOrderPatternMessage)
                .to("t-commodity-pattern-one", Produced.with(stringSerde, orderPatternMessageJsonSerde));

        stream
                .filter(CommodityStreamUtil.isLargeQuantity())
                .mapValues(CommodityStreamUtil::convertToOrderRewardMessage)
                .to("t-commodity-reward-one", Produced.with(stringSerde, orderRewardMessageJsonSerde));

        stream
                .to("t-commodity-storage-one", Produced.with(stringSerde, orderMessageJsonSerde));

        return stream;
    }
}
