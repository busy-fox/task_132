package com.elinvar.customer.publisher;

import com.elinvar.customer.message.OnboardCustomerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnboardCustomerPublisher {

    private String onboardCustomerTopic;

    @Value("${topic.output.onboard-customer}")
    public void setOnboardCustomerTopic(String onboardCustomerTopic) {
        this.onboardCustomerTopic = onboardCustomerTopic;
    }

    private final KafkaTemplate<String, String> template;

    @Autowired
    public OnboardCustomerPublisher(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void publish(OnboardCustomerMessage onboardCustomerMessage) {
        log.info("Publish -> {}", onboardCustomerMessage);
        template.send(onboardCustomerTopic, onboardCustomerMessage.toJson());
    }
}
