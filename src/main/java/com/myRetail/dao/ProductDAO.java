package com.myRetail.dao;

import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import com.myRetail.domain.ProductPriceDTO;
import org.apache.log4j.Logger;

public class ProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductDAO.class);

    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String DB_NAME = "myRetail";
    private static final String COLLECTION_NAME = "products";

    private DBCollection products;

    public ProductDAO() {
        try {
            MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
            DB productDatabase = mongo.getDB(DB_NAME);
            this.products = productDatabase.getCollection(COLLECTION_NAME);
        } catch (UnknownHostException e) {
            LOG.error("Unable to initialize MongoDB client: " + e.getMessage());
            throw new IllegalStateException("Unable to initialize MongoDB client.");
        }
    }

    public void saveProductPrices(ProductPriceDTO productPriceDTO) {
        DBObject product = new BasicDBObject("_id", productPriceDTO.getId())
                                     .append("current_price", new BasicDBObject("value", productPriceDTO.getPrice().getValue())
                                                                            .append("currency_code", productPriceDTO.getPrice().getCurrency()));
        products.save(product);
    }

    public ProductPriceDTO getProductPrice(String id) {
        DBObject result = products.findOne(new BasicDBObject("_id", Long.parseLong(id)));

        result.put("id", id);
        result.removeField("_id");

        ProductPriceDTO productPriceDTO;
        try {
            productPriceDTO =  new ObjectMapper().readValue(JSON.serialize(result), ProductPriceDTO.class);
        } catch (Exception e) {
            LOG.error("Error deserializing request payload for read: " + e.getMessage());
            throw new IllegalStateException("Database  is in a bad state: unable to deserialize document.");
        }
        LOG.info("Returning DAO");
        return productPriceDTO;
    }
}
