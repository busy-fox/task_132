package com.elinvar.order.message.input;


import com.elinvar.order.message.BaseMessage;
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

    private String action;

}