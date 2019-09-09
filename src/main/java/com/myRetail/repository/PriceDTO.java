package com.myRetail.repository;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class PriceDTO {
    @Field("value")
    private double value;

    @Field("currency_code")
    private String currency_code;
}
