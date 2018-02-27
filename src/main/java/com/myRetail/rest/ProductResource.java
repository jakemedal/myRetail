package com.myRetail.rest;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.status;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.JsonNode;
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

		return status(OK)
				.entity(new ObjectMapper().writeValueAsString(responseDTO))
				.build();
	}

	@PUT
	@Path("/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProductPrice(@PathParam("productId") String productId,
									   String payload) throws IOException {
		LOG.info("HTTP PUT /products test - productId=" + productId + " payload=" + payload);

		validateRequest(productId, payload);
		service.putProductPrice(payload);

		return status(CREATED).build();
	}

	private void validateRequest(String productId, String payload) throws IOException {
		if (!getId(payload).equals(productId)) {
			throw new IllegalArgumentException("ID provided as path parameter does not match the ID in the payload\n\n" +
					"PathParam: " + productId +
					"Payload: " + getId(payload));
		}
	}

	private String getId(String payload) throws IOException {
		JsonNode json =  new ObjectMapper().readTree(payload);
		return json.get("id").asText();
	}

}
