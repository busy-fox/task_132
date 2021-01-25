package com.elinvar.order.event;

import com.elinvar.order.service.AccountService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OnboardCustomerEventListener implements ApplicationListener<OnboardCustomerEvent> {

    private final AccountService accountService;

    @Autowired
    public OnboardCustomerEventListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(OnboardCustomerEvent event) {
        log.info("Received new customer data");

        String customerId = event.getCustomerId();
        BigDecimal balance = event.getBalance();

        accountService.createAccount(customerId, balance);
    }
}
