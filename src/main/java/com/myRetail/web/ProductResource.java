package com.myRetail.web;

import com.myRetail.domain.Product;
import com.myRetail.domain.ProductPrice;
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
    public @ResponseBody
    Product getProduct(@PathVariable("productId") String productId) {
		LOG.info("HTTP GET /products - productId=" + productId);
		return productService.getProduct(productId);
	}

	@PutMapping(value = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	public HttpEntity updateProductPrice(@PathVariable("productId") String productId,
                                         @RequestBody ProductPrice productPrice) {
		LOG.info("HTTP PUT /products test - productId=" + productId + " payload=" + productPrice);
		validateRequest(productId, productPrice);
		productService.putProductPrice(productPrice);
		return new HttpEntity<>(HttpStatus.CREATED);
	}

    private void validateRequest(String productId, ProductPrice product) {
	    if (!productId.equals(String.valueOf(product.getId()))) {
	        // TODO: Map this exception!
	        throw new RequestPathParmaMismatchException(
	                "The ID provided in the path does not match the ID provided in the payload."
            );
        }
    }

}