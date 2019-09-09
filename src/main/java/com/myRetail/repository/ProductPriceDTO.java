package com.myRetail.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "products")
public class ProductPriceDTO {
    @Id
    @Field("id")
    private long id;

    @Field("current_price")
    @JsonProperty("current_price")
    private PriceDTO price;
}
