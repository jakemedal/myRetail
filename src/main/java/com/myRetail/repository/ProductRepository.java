package com.myRetail.repository;

public interface ProductRepository {
    void save(ProductPriceDTO productPriceDTO);
    ProductPriceDTO getProductPriceById(String id);
}
