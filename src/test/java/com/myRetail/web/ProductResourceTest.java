package com.myRetail.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.domain.Price;
import com.myRetail.domain.Product;
import com.myRetail.domain.ProductPrice;
import com.myRetail.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductResource.class)
public class ProductResourceTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String PRODUCT_ID = "12345";
    private static final String PRODUCT_PATH = "/products/" + PRODUCT_ID;
    private static final Price PRICE = new Price(42, "KEK");
    private static final Product PRODUCT = new Product(Long.parseLong(PRODUCT_ID), "test product", PRICE);
    private static final ProductPrice PRODUCT_PRICE = new ProductPrice(Long.parseLong(PRODUCT_ID), PRICE);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void canaryTest() {
        assert mockMvc != null;
    }

    @Test
    public void getProductById() throws Exception {
        when(productService.getProduct(PRODUCT_ID))
                .thenReturn(PRODUCT);

        mockMvc.perform(get(PRODUCT_PATH)
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string(serializeProduct(PRODUCT)));

        verify(productService)
                .getProduct(PRODUCT_ID);
    }

    @Test
    public void putProductById() throws Exception {
        mockMvc.perform(put(PRODUCT_PATH)
                                .content(serializeProduct(PRODUCT))
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());

        verify(productService)
                .putProductPrice(PRODUCT_PRICE);
    }

    @Test
    public void getUnsupportedAcceptType() throws Exception {
        mockMvc.perform(get(PRODUCT_PATH)
                                .accept(MediaType.APPLICATION_XML))
               .andExpect(status().isNotAcceptable());
    }

    @Test
    public void getUnsupportedContentType() throws Exception {
        mockMvc.perform(put(PRODUCT_PATH)
                                .contentType(MediaType.APPLICATION_XML))
               .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void useUnknownPath() throws Exception {
        mockMvc.perform(get("/foo/bar"))
               .andExpect(status().isNotFound());

        mockMvc.perform(put("/foo/bar"))
               .andExpect(status().isNotFound());
    }

    private String serializeProduct(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsString(product);
    }
}
