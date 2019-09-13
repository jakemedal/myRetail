package com.myRetail.service;

import com.myRetail.domain.Price;
import com.myRetail.domain.ProductPrice;
import com.myRetail.domain.Product;
import com.myRetail.repository.ProductPriceDao;
import com.myRetail.service.exception.ProductPriceNotFoundException;
import com.myRetail.service.exception.ProductNameNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyRetailProductService implements ProductService {

    private ProductPriceDao productPriceDao;
    private ProductNameClient productNameClient;

    @Autowired
    public MyRetailProductService(ProductPriceDao productPriceDao, ProductNameClient productNameClient) {
        this.productPriceDao = productPriceDao;
        this.productNameClient = productNameClient;
    }

    public Product getProduct(String id) {
        Product product = new Product();

        // Get product name using external API client
        String productName = productNameClient.getProductName(id)
                                              .orElseThrow(() -> new ProductNameNotFoundException(
                                                      "No product title information is available for ID: " + id
                                               ));
        product.setName(productName);

        // Get product price from database
        ProductPrice productPrice = productPriceDao.get(id)
                                                   .orElseThrow(() -> new ProductPriceNotFoundException(
                                                           "No product price information is available for ID: " + id
                                                    ));

        // Set properties on product
        product.setId(productPrice.getId());
        Price price = productPrice.getPrice();
        product.setPrice(new Price(price.getValue(), price.getCurrencyCode()));

        return product;
    }

    public void putProductPrice(ProductPrice productPrice) {
        productPriceDao.save(productPrice);
    }

}
