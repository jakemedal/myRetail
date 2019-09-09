package com.myRetail.web;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.domain.ProductResponseDTO;
import com.myRetail.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
class ProductResource {
	private static final Logger LOG = Logger.getLogger(ProductResource.class);

	private final ProductService productService;

	@Autowired
	public ProductResource(ProductService productService){
	    this.productService = productService;
    }

	@GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<String> getProduct(@PathVariable("productId") String productId) {
		LOG.info("HTTP GET /products - productId=" + productId);

		ProductResponseDTO responseDTO = productService.getProduct(productId);
		String responseJson = convertToJson(responseDTO);

		return new HttpEntity<>(responseJson);
	}

	@PutMapping(value = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	public HttpEntity updateProductPrice(@PathVariable("productId") String productId,
                                         @RequestBody String payload) {
		LOG.info("HTTP PUT /products test - productId=" + productId + " payload=" + payload);

        productService.putProductPrice(productId, payload);
		return new HttpEntity<>(HttpStatus.CREATED);
	}

	private String convertToJson(ProductResponseDTO responseDTO) {
		String json;
		try {
			json = new ObjectMapper().writeValueAsString(responseDTO);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Serialization error: server side data is malformed.");
		}
		return json;
	}

}
