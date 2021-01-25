package com.elinvar.order.repository;

import com.elinvar.order.entities.AccountOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<AccountOrder, Long> {
}