package com.elinvar.order.publisher;

import com.elinvar.order.message.output.AccountBalanceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountBalancePublisher {

    @Value("${topic.output.customer-balance}")
    private String customerTopic;

    private KafkaTemplate<String, String> template;

    @Autowired
    public AccountBalancePublisher(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void publish(AccountBalanceMessage updateCustomerFundMessage) {
        log.info("Publish -> {}", customerTopic);
        template.send(customerTopic, updateCustomerFundMessage.toJson());
    }
}
