package com.myRetail.service;

import com.myRetail.domain.Product;
import com.myRetail.domain.ProductPrice;

public interface ProductService {
    Product getProduct(String id);
    void putProductPrice(ProductPrice productPrice);
}
