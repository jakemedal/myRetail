package com.myRetail.service;

import com.myRetail.domain.ProductResponseDTO;

public interface IProductService {
    ProductResponseDTO getProduct(String id);
    void putProductPrice(String id, String payload);
}
