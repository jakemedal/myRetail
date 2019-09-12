package com.myRetail.web;

import com.myRetail.domain.Product;
import com.myRetail.domain.ProductPrice;
import com.myRetail.service.ProductService;
import com.myRetail.web.exception.NoProductPriceRequestBodyException;
import com.myRetail.web.exception.RequestPathParmaMismatchException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
class ProductResource {

	private final ProductService productService;

	@Autowired
	public ProductResource(ProductService productService){
	    this.productService = productService;
    }

	@GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product getProduct(@PathVariable("productId") String productId) {
		log.info("HTTP GET /products - productId=" + productId);
		return productService.getProduct(productId);
	}

	@PutMapping(value = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	public HttpEntity updateProductPrice(@PathVariable("productId") String productId,
                                         @RequestBody ProductPrice productPrice) {
		log.info("HTTP PUT /products test - productId=" + productId + " payload=" + productPrice);
		validateRequest(productId, productPrice);
		productService.putProductPrice(productPrice);
		return new HttpEntity<>(HttpStatus.CREATED);
	}

    private void validateRequest(String productId, ProductPrice product) {
	    if (!productId.equals(String.valueOf(product.getId()))) {
	        throw new RequestPathParmaMismatchException(
	                "The ID provided in the path (" + productId + ")" +
                            " does not match the ID provided in the payload (" + product.getId() + ")."
            );
        }

	    // TODO: try to make this check using a custom HttpMesageConverter
	    if (product.getPrice() == null) {
	        throw new NoProductPriceRequestBodyException(
	                "No Product Price information was provided in the request body."
            );
        }
    }
}
