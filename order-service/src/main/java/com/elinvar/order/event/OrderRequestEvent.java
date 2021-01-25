package com.elinvar.order.event;

import com.elinvar.order.message.input.OrderRequestMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderRequestEvent extends ApplicationEvent {

    private final String customerId;

    private final String isin;

    private final int quantity;

    public OrderRequestEvent(OrderRequestMessage orderRequestMessage) {
        super(orderRequestMessage);
        this.customerId = orderRequestMessage.getCustomerId();
        this.isin = orderRequestMessage.getIsin();
        this.quantity = orderRequestMessage.getQuantity();
    }
}
