package com.myRetail.rest;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.status;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.domain.ProductResponseDTO;
import com.myRetail.service.ProductService;
import org.apache.log4j.Logger;

@Path("/products")
public class ProductResource {
	private static final Logger LOG = Logger.getLogger(ProductResource.class);
	private final ProductService service = new ProductService();

	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("productId") String productId) throws IOException {
		LOG.info("HTTP GET /products - productId=" + productId);

		ProductResponseDTO responseDTO = service.getProduct(productId);
		String json = new ObjectMapper().writeValueAsString(responseDTO);

		return status(OK)
				.entity(json)
				.build();
	}

	@PUT
	@Path("/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProductPrice(@PathParam("productId") String productId,
									   String payload) {
		LOG.info("HTTP PUT /products test- productId=" + productId + " payload=" + payload);

		//TODO: Verify id in path matches id in payload

		service.putProductPrice(payload);
		return status(CREATED).build();
	}

}
