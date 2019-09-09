package com.myRetail.repository;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoDBProductRepository implements ProductRepository {
    private static final Logger LOG = Logger.getLogger(MongoDBProductRepository.class);

    private final MongoOperations operations;

    @Autowired
    public MongoDBProductRepository(MongoOperations operations) {
        Assert.notNull(operations, "MongoOperations cannot be null.");
        this.operations = operations;
    }

    public void save(ProductPriceDTO productPriceDTO) {
        operations.save(productPriceDTO);
    }

    public ProductPriceDTO getProductPriceById(String id) {
        Query query = Query.query(where("_id").is(Long.parseLong(id)));
        ProductPriceDTO productPriceDTO = operations.findOne(query, ProductPriceDTO.class);

        if (productPriceDTO == null) {
            // Should this be a client error (404) or server error (500)?
            throw new NoSuchElementException("No product price information found for ID: " + id);
        }

        LOG.info("Returning productPriceDto=" + productPriceDTO);
        return productPriceDTO;
    }
}
