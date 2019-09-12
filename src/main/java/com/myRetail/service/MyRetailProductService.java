package com.myRetail.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myRetail.domain.Price;
import com.myRetail.domain.ProductPrice;
import com.myRetail.repository.MongoDBProductRepository;
import com.myRetail.repository.PriceDTO;
import com.myRetail.repository.ProductPriceDTO;
import com.myRetail.domain.Product;
import com.myRetail.repository.ProductRepository;
import com.myRetail.service.exception.ProductTitleNotFoundException;
import com.myRetail.service.exception.UnexpectedExternalApiException;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MyRetailProductService implements ProductService {
    private ProductRepository productRepository;

    private static final Logger LOG = Logger.getLogger(MyRetailProductService.class);
    private static final String EXTERNAL_PRODUCT_RESOURCE = "http://redsky.target.com/v2/pdp/tcin/";
    private static final String EXTERNAL_PRODUCT_QUERY = "?excludes=taxonomy,price,promotion,bulk_ship," +
                                                         "rating_and_review_reviews,rating_and_review_statistics," +
                                                         "question_answer_statistics,deep_red_labels," +
                                                         "available_to_promise_network";

    @Autowired
    public MyRetailProductService(MongoDBProductRepository mongoDBProductRepository) {
        this.productRepository = mongoDBProductRepository;
    }

    public Product getProduct(String id) {
        Product product = new Product();

        // Get product name from external API
        String productName = getProductName(id);
        product.setName(productName);

        // Get product price from NoSQL database
        ProductPriceDTO productPriceDTO = productRepository.getProductPriceById(id);

        // Set properties on product
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

    private String getProductName(String id) {
        HttpResponse<JsonNode> jsonResponse;
        try{
           jsonResponse = Unirest.get(buildRequest(id)).asJson();
        } catch (UnirestException e) {
            throw new UnexpectedExternalApiException("Unable to make HTTP GET request to " + buildRequest(id));
        }

        verifyResponse(jsonResponse, id);
        return getProductTitleFromJson(jsonResponse);
    }

    private String buildRequest(String id) {
        return EXTERNAL_PRODUCT_RESOURCE + id + EXTERNAL_PRODUCT_QUERY;
    }

    private void verifyResponse(HttpResponse<JsonNode> jsonResponse, String id) {
        if (isNotFound(jsonResponse)) {
            throw new ProductTitleNotFoundException("No product title information exists for ID: " + id);
        }

        if (jsonResponse.getStatus() != HttpStatus.OK.value()) {
            throw new UnexpectedExternalApiException(
                    "Unable to retrieve data from an external API. Response code: " + jsonResponse.getStatus()
            );
        }
    }

    private boolean isNotFound(HttpResponse<JsonNode> jsonResponse) {
        return jsonResponse.getStatus() == HttpStatus.NOT_FOUND.value() ||
                getItemFromJson(jsonResponse).length() == 0;
    }

    private String getProductTitleFromJson(HttpResponse<JsonNode> jsonResponse) {
        return getItemFromJson(jsonResponse).getJSONObject("product_description").getString("title");
    }
    
    private JSONObject getItemFromJson(HttpResponse<JsonNode> jsonResponse) {
        return jsonResponse.getBody().getObject().getJSONObject("product").getJSONObject("item");
    }

}
