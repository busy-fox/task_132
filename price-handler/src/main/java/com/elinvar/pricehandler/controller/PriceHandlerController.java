package com.elinvar.pricehandler.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceHandlerController {

    @GetMapping("/getPrice/{isin}")
    public ResponseEntity<String> returnPrice(@PathVariable (value = "isin") String isin)
    {
        BigDecimal price = new BigDecimal(BigInteger.valueOf(new Random().nextInt(501)), 1);
        return ResponseEntity.ok().body(price.toString());
    }
}
