package com.elinvar.order.service;

import com.elinvar.order.entities.Account;
import com.elinvar.order.message.output.AccountBalanceMessage;
import com.elinvar.order.publisher.AccountBalancePublisher;
import com.elinvar.order.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    private AccountBalancePublisher accountBalancePublisher;

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountBalancePublisher updateCustomerFundPublisher,
            AccountRepository accountRepository) {
        this.accountBalancePublisher = updateCustomerFundPublisher;
        this.accountRepository = accountRepository;
    }

    public void createAccount(String customerId, BigDecimal balance) {

        Optional<Account> accountOptional = accountRepository.findByCustomerId(customerId);
        Account account;
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            account.setBalance(balance);
            accountRepository.save(account);
        } else {
            account = accountRepository.save(
                    Account.builder()
                            .customerId(customerId)
                            .balance(balance)
                            .build());
        }

        accountBalancePublisher.publish(
                AccountBalanceMessage.builder()
                        .accountId(account.getId())
                        .balance(balance)
                        .build()
        );
    }

    public void updateBalance(Account account, BigDecimal balance) {
        account.setBalance(balance);
        account = accountRepository.save(account);

        accountBalancePublisher.publish(
                AccountBalanceMessage.builder()
                        .accountId(account.getId())
                        .balance(balance)
                        .build());
        log.info("Customer balance was updated");
    }

    public Optional<Account> getAccount(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
