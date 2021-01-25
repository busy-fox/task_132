package com.elinvar.order.event;

import com.elinvar.order.entities.Account;
import com.elinvar.order.service.AccountService;
import com.elinvar.order.service.OrderService;
import com.elinvar.order.service.PriceService;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OrderRequestEventListener implements ApplicationListener<OrderRequestEvent> {

    private final OrderService orderService;
    private final AccountService accountService;
    private final PriceService priceService;

    @Autowired
    public OrderRequestEventListener(OrderService orderService, AccountService accountService,
            PriceService priceService) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.priceService = priceService;
    }

    @Override
    public void onApplicationEvent(OrderRequestEvent event) {
        log.info("Received order request");

        String customerId = event.getCustomerId();
        String isin = event.getIsin();
        int quantity = event.getQuantity();

        Optional<Account> optionalAccount = accountService.getAccount(customerId);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            BigDecimal isinPrice = null;
            try {
                isinPrice = priceService.getPrice(isin);
                BigDecimal orderPrice = isinPrice.multiply(new BigDecimal(quantity));
                BigDecimal oldBalance = account.getBalance();

                // subtract balance twice without validation
                BigDecimal newBalance = oldBalance
                        .subtract(orderPrice)
                        .subtract(orderPrice);

                accountService.updateBalance(account, newBalance);
                orderService.completeOrder(account.getId(), isin, quantity, isinPrice);


            } catch (UnirestException e) {
                log.info("Price handler error", e);
                orderService.createFailedOrder(account.getId(), isin, quantity, "MISSING_PRICE");
            }

        } else {
            log.error("There is no open account for {} customer, order canceled", customerId);
        }
    }

}
