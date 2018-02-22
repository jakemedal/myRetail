package com.myRetail.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/products")
public class ProductResource {
	private static final Logger logger = Logger.getLogger(ProductResource.class);

	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String sayhello(@PathParam("productId") String productId) {
		logger.info("HTTP GET /products - productId=" + productId);

		return "foo";
	}

}
