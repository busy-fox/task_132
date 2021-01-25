package com.elinvar.order.event;

import com.elinvar.order.message.input.OnboardCustomerMessage;
import com.elinvar.order.message.input.OrderRequestMessage;
import java.math.BigDecimal;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnboardCustomerEvent extends ApplicationEvent {

    private final String customerId;
    
    private final BigDecimal balance;

    public OnboardCustomerEvent(OnboardCustomerMessage onboardCustomerMessage) {
        super(onboardCustomerMessage);
        this.customerId = onboardCustomerMessage.getCustomerId();
        this.balance = onboardCustomerMessage.getBalance();
    }
}
