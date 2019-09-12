package com.myRetail.service;

import com.myRetail.domain.Price;
import com.myRetail.domain.ProductPrice;
import com.myRetail.repository.PriceDTO;
import com.myRetail.repository.ProductPriceDTO;
import com.myRetail.domain.Product;
import com.myRetail.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyRetailProductService implements ProductService {

    private ProductRepository productRepository;
    private ProductNameClient productNameClient;

    @Autowired
    public MyRetailProductService(ProductRepository productRepository, ProductNameClient productNameClient) {
        this.productRepository = productRepository;
        this.productNameClient = productNameClient;
    }

    public Product getProduct(String id) {
        Product product = new Product();

        // Get product name using external API client
        String productName = productNameClient.getProductName(id);
        product.setName(productName);

        // Get product price from database
        ProductPriceDTO productPriceDTO = productRepository.getProductPriceById(id);

        // Set properties on product
        log.info("Got here with productPriceDTO: " + productPriceDTO);
        product.setId(productPriceDTO.getId());
        PriceDTO priceDTO = productPriceDTO.getPrice();
        product.setPrice(new Price(priceDTO.getValue(), priceDTO.getCurrency_code()));

        return product;
    }

    public void putProductPrice(ProductPrice productPrice) {
        ProductPriceDTO productPriceDTO = new ProductPriceDTO();
        productPriceDTO.setId(productPrice.getId());

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setValue(productPrice.getPrice().getValue());
        priceDTO.setCurrency_code(productPrice.getPrice().getCurrencyCode());
        productPriceDTO.setPrice(priceDTO);

        productRepository.save(productPriceDTO);
    }

}
