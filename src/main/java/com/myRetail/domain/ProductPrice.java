package com.myRetail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductPrice {
    @JsonProperty("id")
    private long id;

    @JsonProperty("current_price")
    private Price price;
}
