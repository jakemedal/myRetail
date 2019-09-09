package com.myRetail.repository;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class PriceDTO {
    @Field("value")
    private double value;

    @Field("currency_code")
    private String currency_code;

    public PriceDTO(double value, String currency) {
        this.value = value;
        this.currency_code = currency;
    }

    public double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency_code;
    }

}
