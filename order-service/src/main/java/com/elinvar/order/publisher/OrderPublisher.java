package com.elinvar.order.publisher;

import com.elinvar.order.message.output.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderPublisher {

    @Value("${topic.output.create-order}")
    private String orderTopic;

    private final KafkaTemplate<String, String> template;

    @Autowired
    public OrderPublisher(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void publish(OrderMessage createOrderMessage) {
        log.info("Publish -> {}", orderTopic);
        template.send(orderTopic, createOrderMessage.toJson());
    }
}
