package com.elinvar.customer.service;

import com.elinvar.customer.message.OrderRequestMessage;
import com.elinvar.customer.publisher.OrderRequestPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderRequestService {

    private OrderRequestPublisher orderRequestPublisher;

    @Autowired
    public OrderRequestService(OrderRequestPublisher orderRequestPublisher) {
        this.orderRequestPublisher = orderRequestPublisher;
    }

    public void createOrderRequest(String customerId, String isin, int quantity) {
        orderRequestPublisher.publish(
                OrderRequestMessage.builder()
                        .customerId(customerId)
                        .isin(isin)
                        .quantity(quantity)
                        .build()
        );
    }
}
