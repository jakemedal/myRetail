package com.myRetail.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPriceDTO {

    private long id;
    private PriceDTO price;

    public ProductPriceDTO(long id, PriceDTO price) {
        this.id = id;
        this.price = price;
    }

    public ProductPriceDTO() {
        super();
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("current_price")
    public PriceDTO getPrice() {
        return price;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("current_price")
    public void setPrice(PriceDTO price) {
        this.price = price;
    }

}
