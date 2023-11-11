package org.example.util;

import lombok.experimental.UtilityClass;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.logging.log4j.util.Strings;
import org.example.message.OrderMessage;
import org.example.message.OrderPatternMessage;
import org.example.message.OrderRewardMessage;

@UtilityClass
public class CommodityStreamUtil {

    public static OrderMessage maskCreditCard(OrderMessage message) {
        var copy = message.copy();
        copy.setCreditCardNumber(copy.getCreditCardNumber().replaceFirst("\\d{12}", Strings.repeat("*", 12)));
        return copy;
    }

    public static OrderPatternMessage convertToOrderPatternMessage(OrderMessage message) {
        var orderPatternMessage = new OrderPatternMessage();

        orderPatternMessage.setItemName(message.getItemName());
        orderPatternMessage.setOrderNumber(message.getOrderNumber());
        orderPatternMessage.setOrderLocation(message.getOrderLocation());
        orderPatternMessage.setOrderDateTime(message.getOrderDateTime());
        orderPatternMessage.setTotalItemAmount((long) message.getPrice() * message.getQuantity());

        return orderPatternMessage;
    }

    public static OrderRewardMessage convertToOrderRewardMessage(OrderMessage message) {
        var orderRewardMessage = new OrderRewardMessage();

        orderRewardMessage.setItemName(message.getItemName());
        orderRewardMessage.setOrderNumber(message.getOrderNumber());
        orderRewardMessage.setOrderLocation(message.getOrderLocation());
        orderRewardMessage.setOrderDateTime(message.getOrderDateTime());
        orderRewardMessage.setPrice(message.getPrice());
        orderRewardMessage.setQuantity(message.getQuantity());

        return orderRewardMessage;
    }

    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, value) -> value.getQuantity() > 200;
    }
}
