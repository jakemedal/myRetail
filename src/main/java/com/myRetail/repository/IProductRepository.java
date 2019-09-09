package com.myRetail.repository;

import org.springframework.data.repository.Repository;

public interface IProductRepository extends Repository<ProductPriceDTO, Long> {
    void save(ProductPriceDTO productPriceDTO);
    ProductPriceDTO getProductPriceById(String id);
}
