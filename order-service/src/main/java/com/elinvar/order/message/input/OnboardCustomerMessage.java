package com.elinvar.order.message.input;


import com.elinvar.order.message.BaseMessage;
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