package com.myRetail.service;

import java.util.Optional;

public interface ProductNameClient {
    Optional<String> getProductName(String id);
}
