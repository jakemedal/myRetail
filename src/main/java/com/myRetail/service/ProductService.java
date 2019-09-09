package com.myRetail.service;

import java.io.IOException;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myRetail.repository.ProductRepository;
import com.myRetail.repository.ProductPriceDTO;
import com.myRetail.domain.ProductResponseDTO;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    private ProductRepository productRepository;

    private static final Logger LOG = Logger.getLogger(ProductService.class);
    private static final String EXTERNAL_PRODUCT_RESOURCE = "http://redsky.target.com/v2/pdp/tcin/";
    private static final String EXTERNAL_PRODUCT_QUERY = "?excludes=taxonomy,price,promotion,bulk_ship," +
                                                         "rating_and_review_reviews,rating_and_review_statistics," +
                                                         "question_answer_statistics,deep_red_labels," +
                                                         "available_to_promise_network";

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO getProduct(String id) {

        ProductResponseDTO responseDTO = new ProductResponseDTO();

        // Get product name from external API
        String productName = getProductName(id);
        responseDTO.setName(productName);

        // Get product price from NoSQL database
        ProductPriceDTO productPriceDTO = productRepository.getProductPriceById(id);
        responseDTO.setId(productPriceDTO.getId());
        responseDTO.setPrice(productPriceDTO.getPriceDto());

        // Return response object to rest layer
        return responseDTO;
    }

    public void putProductPrice(String id, String payload) {
        validateRequest(id, payload);

        ProductPriceDTO productPriceDTO;
        try {
            productPriceDTO = new ObjectMapper().readValue(payload, ProductPriceDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to parse payload.");
        }
        productRepository.save(productPriceDTO);
    }

    private void validateRequest(String productId, String payload) {
        if (!getId(payload).equals(productId)) {
            throw new IllegalArgumentException("ID provided as path parameter does not match the ID in the payload\n\n" +
                    "PathParam: " + productId +
                    "Payload: " + getId(payload));
        }
    }

    private String getId(String payload) {
        com.fasterxml.jackson.databind.JsonNode json;
        try {
            json = new ObjectMapper().readTree(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to parse payload.");
        }
        return json.get("id").asText();
    }

    private String getProductName(String id) {
        HttpResponse<JsonNode> jsonResponse;
        try{
           jsonResponse = Unirest.get(buildRequest(id)).asJson();
        } catch (UnirestException e) {
            LOG.error("External HTTP GET request failed: " + buildRequest(id));
            throw new IllegalStateException("Unable to make HTTP GET request to " + buildRequest(id));
        }

        verifyResponse(jsonResponse, id);
        return getProductTitleFromJson(jsonResponse);
    }

    private String buildRequest(String id) {
        return EXTERNAL_PRODUCT_RESOURCE + id + EXTERNAL_PRODUCT_QUERY;
    }

    private void verifyResponse(HttpResponse<JsonNode> jsonResponse, String id) {
        JSONObject item = getItemFromJson(jsonResponse);
        if (item.length() == 0) throw new NoSuchElementException("No product title information exists for ID: " + id);
    }

    private String getProductTitleFromJson(HttpResponse<JsonNode> jsonResponse) {
        return getItemFromJson(jsonResponse).getJSONObject("product_description").getString("title");
    }
    
    private JSONObject getItemFromJson(HttpResponse<JsonNode> jsonResponse) {
        return jsonResponse.getBody().getObject().getJSONObject("product").getJSONObject("item");
    }

}
