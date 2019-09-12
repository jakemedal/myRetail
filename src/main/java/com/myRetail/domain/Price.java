package com.myRetail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
public
class Price {

    @Field("value")
    @JsonProperty("value")
    private double value;

    @Field("currency_code")
    @JsonProperty("currency_code")
    private String currencyCode;
}
