package com.myRetail.repository;

import com.myRetail.repository.exception.ProductPriceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Log4j2
@Repository
public class MongoDBProductRepository implements ProductRepository {
    private final MongoOperations operations;

    @Autowired
    public MongoDBProductRepository(MongoOperations operations) {
        this.operations = operations;
    }

    public void save(ProductPriceDTO productPriceDTO) {
        operations.save(productPriceDTO);
    }

    public ProductPriceDTO getProductPriceById(String id) {
        Query query = Query.query(where("_id").is(Long.parseLong(id)));
        ProductPriceDTO productPriceDTO = operations.findOne(query, ProductPriceDTO.class);

        if (productPriceDTO == null) {
            throw new ProductPriceNotFoundException("No product price information found for ID: " + id);
        }

        return productPriceDTO;
    }
}
