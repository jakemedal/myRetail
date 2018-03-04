package com.myRetail.dao;

import java.net.UnknownHostException;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import com.myRetail.domain.ProductPriceDTO;
import org.apache.log4j.Logger;

public class ProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductDAO.class);

    // TODO: Move to properties file
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

        if (result == null) {
            // Should this be a client error (404) or server error (500)?
            throw new NoSuchElementException("No product price information found for ID: " + id);
        }

        // Fetch _id as id to match payload requirements
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
