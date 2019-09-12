package com.myRetail.repository;

import com.myRetail.domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProductPriceDao implements Dao<ProductPrice> {
    private final MongoOperations operations;

    @Autowired
    public ProductPriceDao(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    public Optional<ProductPrice> get(String id) {
        Query query = Query.query(where("_id").is(Long.parseLong(id)));
        return Optional.ofNullable(operations.findOne(query, ProductPrice.class));
    }

    @Override
    public void save(ProductPrice productPrice) {
        operations.save(productPrice);
    }
}
