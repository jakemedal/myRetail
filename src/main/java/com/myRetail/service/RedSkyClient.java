package com.myRetail.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.myRetail.service.exception.ProductNameNotFoundException;
import com.myRetail.service.exception.UnexpectedExternalApiException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class RedSkyClient implements ProductNameClient {
    private static final String EXTERNAL_PRODUCT_RESOURCE = "http://redsky.target.com/v2/pdp/tcin/";
    private static final String EXTERNAL_PRODUCT_QUERY = "?excludes=taxonomy,price,promotion,bulk_ship," +
            "rating_and_review_reviews,rating_and_review_statistics," +
            "question_answer_statistics,deep_red_labels," +
            "available_to_promise_network";

    @Override
    public Optional<String> getProductName(String id) {
        HttpResponse<JsonNode> jsonResponse;
        try{
            jsonResponse = Unirest.get(buildRequest(id)).asJson();
        } catch (UnirestException e) {
            throw new UnexpectedExternalApiException("Unable to make HTTP GET request to " + buildRequest(id));
        }

        verifyResponse(jsonResponse, id);
        return Optional.of(getProductTitleFromJson(jsonResponse));
    }

    private String buildRequest(String id) {
        return EXTERNAL_PRODUCT_RESOURCE + id + EXTERNAL_PRODUCT_QUERY;
    }

    private void verifyResponse(HttpResponse<JsonNode> jsonResponse, String id) {
        if (isNotFound(jsonResponse)) {
            throw new ProductNameNotFoundException("No product title information exists for ID: " + id);
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
