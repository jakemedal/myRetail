package com.myRetail.repository;

import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<ProductPriceDTO, Long> {
    void save(ProductPriceDTO productPriceDTO);
    ProductPriceDTO getProductPriceById(String id);
}
