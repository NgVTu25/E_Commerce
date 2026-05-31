package com.ecommerce.vn.controllers;

import com.ecommerce.vn.config.CacheConfig;
import com.ecommerce.vn.config.CorsConfig;
import com.ecommerce.vn.dtos.ProductDTO;
import com.ecommerce.vn.security.JwtAuthenticationFilter;
import com.ecommerce.vn.security.JwtTokenProvider;
import com.ecommerce.vn.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import({CacheConfig.class, CorsConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void getAllProducts_returnsOkAndJsonArray() throws Exception {
        ProductDTO product = sampleProduct(1);
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].productName").value("Chai"));
    }

    @Test
    void getProductById_returnsProduct() throws Exception {
        when(productService.getProductById(1)).thenReturn(sampleProduct(1));

        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Chai"));
    }

    @Test
    void searchProducts_returnsMatchingList() throws Exception {
        when(productService.searchProducts("cha")).thenReturn(List.of(sampleProduct(1)));

        mockMvc.perform(get("/api/product/search").param("keyword", "cha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void createProduct_withInvalidBody_returnsBadRequest() throws Exception {
        ProductDTO invalid = new ProductDTO();

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_withValidBody_delegatesToService() throws Exception {
        ProductDTO request = sampleProduct(null);
        ProductDTO saved = sampleProduct(5);
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

        verify(productService).createProduct(any(ProductDTO.class));
    }

    @Test
    void updateProduct_delegatesToService() throws Exception {
        ProductDTO request = sampleProduct(null);
        when(productService.updateProduct(eq(1), any(ProductDTO.class))).thenReturn(sampleProduct(1));

        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(productService).updateProduct(eq(1), any(ProductDTO.class));
    }

    @Test
    void deleteProduct_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1);
    }

    private static ProductDTO sampleProduct(Integer id) {
        ProductDTO dto = new ProductDTO();
        dto.setId(id);
        dto.setProductName("Chai");
        dto.setUnitPrice(new BigDecimal("18.00"));
        dto.setUnitsInStock(39);
        dto.setUnitsOnOrder(0);
        dto.setReorderLevel(10);
        dto.setDiscontinued(0);
        dto.setCategoryId((short) 1);
        dto.setSupplierId(1);
        return dto;
    }
}
