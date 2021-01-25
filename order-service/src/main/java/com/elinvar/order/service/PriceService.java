package com.elinvar.order.service;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PriceService {


    @Value("${price-handler.url}")
    private String serviceUrl;


    public BigDecimal getPrice(String isin) throws UnirestException {
        return new BigDecimal(
                Unirest.get(serviceUrl + isin).asString().getBody()
        );
    }

}
