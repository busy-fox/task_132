package com.elinvar.order.service;

import com.elinvar.order.entities.AccountOrder;
import com.elinvar.order.message.output.OrderMessage;
import com.elinvar.order.publisher.OrderPublisher;
import com.elinvar.order.repository.OrderRepository;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    private final OrderPublisher orderPublisher;

    private final OrderRepository orderRepository;


    @Autowired
    public OrderService(OrderPublisher createOrderPublisher, OrderRepository orderRepository) {
        this.orderPublisher = createOrderPublisher;
        this.orderRepository = orderRepository;
    }

    public void completeOrder(long accountId, String isin, int quantity, BigDecimal isinPrice) {

        AccountOrder order = AccountOrder.builder()
                .accountId(accountId)
                .isin(isin)
                .quantity(quantity)
                .price(isinPrice)
                .build();

        orderRepository.save(order);

        orderPublisher.publish(
                OrderMessage.builder()
                        .accountId(accountId)
                        .isin(isin)
                        .price(isinPrice)
                        .quantity(quantity)
                        .build()
        );
        log.info("Order was saved");
    }

    public void createFailedOrder(long accountId, String isin, int quantity, String status) {
        AccountOrder order = AccountOrder.builder()
                .accountId(accountId)
                .isin(isin)
                .quantity(quantity)
                .price(new BigDecimal(0))
                .build();

        orderRepository.save(order);

        orderPublisher.publish(
                OrderMessage.builder()
                        .accountId(accountId)
                        .isin(isin)
                        .status(status)
                        .price(new BigDecimal(0))
                        .quantity(quantity)
                        .build()
        );
        log.info("Order was saved");


    }
}
