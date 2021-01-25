package com.elinvar.customer.message;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardCustomerMessage implements BaseMessage {

    private String customerId;

    private BigDecimal balance;

}