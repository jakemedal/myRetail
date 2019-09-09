package com.myRetail.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
public class ProductPriceDTO {

    @Id
    @Field("_id")
    private long id;

    @Field("current_price")
    private PriceDTO price;

    public ProductPriceDTO(long id, PriceDTO price) {
        this.id = id;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public PriceDTO getPriceDto() {
        return price;
    }

}
