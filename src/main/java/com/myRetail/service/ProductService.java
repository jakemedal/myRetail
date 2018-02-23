package com.myRetail.service;


import java.io.IOException;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myRetail.dao.ProductDAO;
import com.myRetail.domain.ProductPriceDTO;
import com.myRetail.domain.ProductResponseDTO;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    private static final Logger LOG = Logger.getLogger(ProductService.class);
    private static final String EXTERNAL_PRODUCT_RESOURCE = "http://redsky.target.com/v2/pdp/tcin/";
    private static final String EXTERNAL_PRODUCT_QUERY = "?excludes=taxonomy,price,promotion,bulk_ship," +
                                                         "rating_and_review_reviews,rating_and_review_statistics," +
                                                         "question_answer_statistics,deep_red_labels," +
                                                         "available_to_promise_network";

    public ProductResponseDTO getProduct(String id) {

        ProductResponseDTO responseDTO = new ProductResponseDTO();

        // 1) Get product name from external API
        //      - If none exists, NoSuchElementException --> 404 Not Found
        String productName = getProductName(id);
        responseDTO.setName(productName);

        // 2) Get product price from NoSQL database
        //      - If none exists, NoSuchElement --> 404 Not Found
        ProductPriceDTO productPriceDTO = dao.getProductPrice(id);

        // 3) Combine product name and price to create ProductResponseDTO
        responseDTO.setId(productPriceDTO.getId());
        responseDTO.setPrice(productPriceDTO.getPrice());

        // 4) Return to rest layer
        return responseDTO;
    }

    public void putProductPrice(String payload) {
        ProductPriceDTO productPriceDTO;
        try {
            productPriceDTO = new ObjectMapper().readValue(payload, ProductPriceDTO.class);
        } catch (IOException e) {
            LOG.error("Error deserializing request payload for write. " +
                    "\n\n Payload=" + payload + "\n\n" + e.getMessage());
            throw new IllegalArgumentException("JSON input is invalid.");
        }
        dao.saveProductPrices(productPriceDTO);
    }

    private String getProductName(String id) { // TODO: Try/Catch and null check here is ugly
        HttpResponse<JsonNode> jsonResponse;
        try{
           jsonResponse = Unirest.get(buildRequest(id)).asJson();
        } catch (UnirestException e) {
            LOG.error("External HTTP GET request failed: " + buildRequest(id));
            throw new IllegalStateException("Unable to make HTTP GET request to " + buildRequest(id));        //TODO: Manage exceptions / Map to HTTP codes
        }
        LOG.info("External HTTP GET request to " + buildRequest(id) + " succeeded. Payload=" + jsonResponse.getBody().toString());

        verifyResponse(jsonResponse);
        return getProductTitleFromJson(jsonResponse);
    }

    private String buildRequest(String id) {
        return EXTERNAL_PRODUCT_RESOURCE + id + EXTERNAL_PRODUCT_QUERY;
    }

    private void verifyResponse(HttpResponse<JsonNode> jsonResponse) {
        JSONObject item = getItemFromJson(jsonResponse);
        if (item.length() == 0) throw new NoSuchElementException();
    }

    private JSONObject getItemFromJson(HttpResponse<JsonNode> jsonResponse) {
        return jsonResponse.getBody().getObject().getJSONObject("product").getJSONObject("item");
    }

    private String getProductTitleFromJson(HttpResponse<JsonNode> jsonResponse) {
        return getItemFromJson(jsonResponse).getJSONObject("product_description").getString("title");
    }

}
