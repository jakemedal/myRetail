package com.myRetail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class Price {
    @JsonProperty("value")
    private double value;

    @JsonProperty("currency_code")
    private String currencyCode;
}
