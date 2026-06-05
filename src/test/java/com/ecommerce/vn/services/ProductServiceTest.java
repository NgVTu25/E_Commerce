package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.ProductDTO;
import com.ecommerce.vn.models.entitis.Categories;
import com.ecommerce.vn.models.entitis.Products;
import com.ecommerce.vn.models.entitis.Suppliers;
import com.ecommerce.vn.repositories.CategoryRepository;
import com.ecommerce.vn.repositories.ProductRepository;
import com.ecommerce.vn.repositories.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_mapsEntitiesToDtos() {
        Products entity = Products.builder().id(1).productName("Chai").build();
        ProductDTO dto = new ProductDTO();
        dto.setProductId(1);
        dto.setProductName("Chai");

        when(productRepository.findAll()).thenReturn(List.of(entity));
        when(modelMapper.map(entity, ProductDTO.class)).thenReturn(dto);

        List<ProductDTO> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getProductName()).isEqualTo("Chai");
    }

    @Test
    void getProductById_whenMissing_throws() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createProduct_setsCategoryAndSupplier() {
        ProductDTO input = new ProductDTO();
        input.setProductName("New");
        input.setUnitPrice(new BigDecimal("10.00"));
        input.setUnitsInStock(5);
        input.setCategoryId((short) 2);
        input.setSupplierId(3);

        Products mapped = Products.builder().productName("New").build();
        Categories category = Categories.builder().id((short) 2).build();
        Suppliers supplier = Suppliers.builder().id(3).build();
        Products saved = Products.builder().id(10).productName("New").build();
        ProductDTO output = new ProductDTO();
        output.setProductId(10);

        when(modelMapper.map(input, Products.class)).thenReturn(mapped);
        when(categoryRepository.findById((short) 2)).thenReturn(Optional.of(category));
        when(supplierRepository.findById(3)).thenReturn(Optional.of(supplier));
        when(productRepository.save(mapped)).thenReturn(saved);
        when(modelMapper.map(any(Products.class), eq(ProductDTO.class))).thenReturn(output);

        ProductDTO result = productService.createProduct(input);

        assertThat(result.getProductId()).isEqualTo(10);
        verify(productRepository).save(mapped);
    }

    @Test
    void deleteProduct_whenNotFound_throws() {
        when(productRepository.existsById(7)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(7))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteProduct_whenExists_deletes() {
        when(productRepository.existsById(7)).thenReturn(true);

        productService.deleteProduct(7);

        verify(productRepository).deleteById(7);
    }

    @Test
    void searchProducts_returnsMatches() {
        Products entity = Products.builder().id(2).productName("Chang").build();
        ProductDTO dto = new ProductDTO();
        dto.setProductName("Chang");

        when(productRepository.findByProductNameContaining("cha")).thenReturn(List.of(entity));
        when(modelMapper.map(entity, ProductDTO.class)).thenReturn(dto);

        List<ProductDTO> result = productService.searchProducts("cha");

        assertThat(result).extracting(ProductDTO::getProductName).containsExactly("Chang");
    }
}
