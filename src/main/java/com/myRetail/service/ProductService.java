package com.myRetail.service;

import com.myRetail.domain.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO getProduct(String id);
    void putProductPrice(String id, String payload);
}
