package org.example.util;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;
import org.example.message.OrderMessage;

@UtilityClass
public class CommodityStreamUtil {

    public static OrderMessage maskCreditCard(OrderMessage message) {
        var copy = message.copy();
        copy.setCreditCardNumber(copy.getCreditCardNumber().replaceFirst("\\d{12}", Strings.repeat("*", 12)));
        return copy;
    }
}
