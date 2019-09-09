package com.myRetail.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.myRetail.repository.PriceDTO;

public class ProductResponseDTO {

    private long id;
    private String name;
    private PriceDTO price;

    public ProductResponseDTO(long id, String name, PriceDTO price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductResponseDTO() {
        super();
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("current_price")
    public PriceDTO getPrice() {
        return price;
    }

    @JsonProperty("current_price")
    public void setPrice(PriceDTO price) {
        this.price = price;
    }

}
