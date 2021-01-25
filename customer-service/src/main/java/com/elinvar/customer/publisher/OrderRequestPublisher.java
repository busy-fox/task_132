package com.elinvar.customer.publisher;

import com.elinvar.customer.message.OrderRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRequestPublisher {

    private String orderRequestTopic;

    @Value("${topic.output.order-request}")
    public void setOrderRequestTopic(String orderRequestTopic) {
        this.orderRequestTopic = orderRequestTopic;
    }

    private final KafkaTemplate<String, String> template;

    @Autowired
    public OrderRequestPublisher(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void publish(OrderRequestMessage orderRequestMessage) {
        log.info("Publish -> {}", orderRequestMessage);
        template.send(orderRequestTopic, orderRequestMessage.toJson());
    }
}
