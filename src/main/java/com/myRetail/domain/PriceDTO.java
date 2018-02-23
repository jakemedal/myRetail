package com.myRetail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceDTO {

    private double value;
    private String currency_code;

    public PriceDTO() {
        super();
    }

    public PriceDTO(double value, String currency) {
        this.value = value;
        this.currency_code = currency;
    }

    @JsonProperty("value")
    public double getValue() {
        return value;
    }

    @JsonProperty("currency_code")
    public String getCurrency() {
        return currency_code;
    }

    @JsonProperty("value")
    public void setValue(double value) {
        this.value = value;
    }

    @JsonProperty("currency_code")
    public void setCurrency(String currency) {
        this.currency_code = currency;
    }
}
