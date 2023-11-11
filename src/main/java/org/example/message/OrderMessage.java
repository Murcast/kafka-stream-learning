package org.example.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private String orderLocation;
    private String orderNumber;
    private String creditCardNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDateTime;

    private String itemName;
    private int price;
    private int quantity;

    public OrderMessage copy() {
        var orderMessage = new OrderMessage();

        orderMessage.setOrderLocation(this.getOrderLocation());
        orderMessage.setOrderNumber(this.getOrderNumber());
        orderMessage.setCreditCardNumber(this.getCreditCardNumber());
        orderMessage.setOrderDateTime(this.getOrderDateTime());
        orderMessage.setItemName(this.getItemName());
        orderMessage.setPrice(this.getPrice());
        orderMessage.setQuantity(this.getQuantity());

        return orderMessage;
    }

}
