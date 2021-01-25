package com.elinvar.order.repository;

import com.elinvar.order.entities.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByCustomerId(String customerId);
}