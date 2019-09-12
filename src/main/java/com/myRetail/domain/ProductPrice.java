package com.myRetail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@Document(collection = "products")
public class ProductPrice {

    @Id
    @Field("id")
    @JsonProperty("id")
    private long id;

    @Field("current_price")
    @JsonProperty("current_price")
    private Price price;
}
