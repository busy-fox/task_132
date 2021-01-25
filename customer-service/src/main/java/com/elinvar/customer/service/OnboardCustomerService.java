package com.elinvar.customer.service;

import com.elinvar.customer.entities.Customer;
import com.elinvar.customer.message.OnboardCustomerMessage;
import com.elinvar.customer.publisher.OnboardCustomerPublisher;
import com.elinvar.customer.repository.CustomerRepository;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnboardCustomerService {

    private OnboardCustomerPublisher onboardCustomerPublisher;

    private CustomerRepository customerRepository;

    @Autowired
    public OnboardCustomerService(OnboardCustomerPublisher onboardCustomerPublisher,
            CustomerRepository customerRepository) {
        this.onboardCustomerPublisher = onboardCustomerPublisher;
        this.customerRepository = customerRepository;
    }

    public void onboardCustomer(String customerId, String name, BigDecimal balance) {
        customerRepository.save(Customer.builder().customerId(customerId).name(name).build());
        onboardCustomerPublisher.publish(
                OnboardCustomerMessage.builder()
                        .customerId(customerId)
                        .balance(balance)
                        .build()
        );
        log.info("Customer {} was onboarded", customerId);
    }
}
