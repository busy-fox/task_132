package com.elinvar.customer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TestService {

    private final Random random = new Random();

    private OrderRequestService orderRequestService;

    private OnboardCustomerService onboardCustomerService;

    private Boolean isActive;

    private int period;


    @Value("${test-mode.active}")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Value("${test-mode.period}")
    public void setPeriod(int period) {
        this.period = period;
    }

    @Autowired
    public TestService(OnboardCustomerService onboardCustomerService,
            OrderRequestService orderRequestService) {
        this.onboardCustomerService = onboardCustomerService;
        this.orderRequestService = orderRequestService;
    }

    private void onboardTestCustomers(List<String> testCustomers) {
        testCustomers.forEach(customerId ->
                onboardCustomerService.onboardCustomer(
                        customerId,
                        "Test Customer",
                        new BigDecimal(10000000))
        );
    }


    @PostConstruct
    private void start() {
        if (isActive) {
            log.info("Test mode is active.");
            // Create test customers
            List<String> testCustomers = List.of("testCustomer001", "testCustomer002", "testCustomer003");
            onboardTestCustomers(testCustomers);

            // Start random orders generation for test customers
            List<String> testAsset = List.of("QA00000001", "QA00000002");

            TimerTask orderRequestTask = new RandomOrderRequestCreationTask(testCustomers, testAsset);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(orderRequestTask, 0, period, TimeUnit.SECONDS);

        }
    }

    private class RandomOrderRequestCreationTask extends TimerTask {

        private List<String> customers;
        private List<String> assets;

        public RandomOrderRequestCreationTask(List<String> customers, List<String> assets) {
            this.customers = customers;
            this.assets = assets;
        }

        @Override
        public void run() {
            String customerId = getRandom(customers);
            String isin = getRandom(assets);
            int quantity = random.nextInt(100);

            orderRequestService.createOrderRequest(customerId, isin, quantity);
            log.info("Fake order {} was created for customer {}.", isin, customerId);
        }

        private String getRandom(List<String> list) {
            return list.get(random.nextInt(list.size()));
        }
    }

}
