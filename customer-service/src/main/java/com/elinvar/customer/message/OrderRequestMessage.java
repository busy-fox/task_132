package com.elinvar.customer.message;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestMessage implements BaseMessage {

    private String customerId;

    private String isin;

    private int quantity;

    @Builder.Default
    private String action = "BUY";

}