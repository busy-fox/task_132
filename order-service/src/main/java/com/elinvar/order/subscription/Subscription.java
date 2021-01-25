package com.elinvar.order.subscription;

import com.elinvar.order.event.OnboardCustomerEvent;
import com.elinvar.order.event.OrderRequestEvent;
import com.elinvar.order.message.input.OnboardCustomerMessage;
import com.elinvar.order.message.input.OrderRequestMessage;
import com.elinvar.order.message.BaseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Subscription {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = "order.request", groupId = "elinvar_case")
    public void listenOrderRequestTopic(String message) {
        try {
            OrderRequestMessage orderRequestMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, OrderRequestMessage.class);
            applicationEventPublisher.publishEvent(
                    new OrderRequestEvent(orderRequestMessage));
            System.out.println("Consumed message in order.request: " + orderRequestMessage);
        } catch (JsonProcessingException e) {
            log.error("Can't parse message.", e);
        }
    }

    @KafkaListener(topics = "onboard.customer", groupId = "elinvar_case")
    public void listenOnboardCustomerTopic(String message) {
        try {
            OnboardCustomerMessage onboardCustomerMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, OnboardCustomerMessage.class);
            applicationEventPublisher.publishEvent(
                    new OnboardCustomerEvent(onboardCustomerMessage));
            System.out.println("Consumed message in order.request: " + onboardCustomerMessage);
        } catch (JsonProcessingException e) {
            log.error("Can't parse message.", e);
        }
    }
    
    @PostConstruct
    private void start() {
        log.info("Start listen order.request topic");
    }
}
