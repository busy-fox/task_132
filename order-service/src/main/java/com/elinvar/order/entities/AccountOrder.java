package com.elinvar.order.entities;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long accountId;

    String isin;

    int quantity;

    BigDecimal price;

    @Builder.Default
    private String status = "COMPLETED";

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
}
