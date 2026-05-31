package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.ProductDTO;
import com.ecommerce.vn.models.entitis.Products;
import com.ecommerce.vn.models.entitis.Categories;
import com.ecommerce.vn.models.entitis.Suppliers;
import com.ecommerce.vn.repositories.ProductRepository;
import com.ecommerce.vn.repositories.CategoryRepository;
import com.ecommerce.vn.repositories.SupplierRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Cacheable(value = "products")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }


    public ProductDTO getProductById(Integer id) {
         Products products = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
         return toDto(products);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO createProduct(ProductDTO productDTO) {
        Products product = modelMapper.map(productDTO, Products.class);

        if (productDTO.getCategoryId() != null) {
            Categories category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category ID không tồn tại"));
            product.setCategory(category);
        }

        if (productDTO.getSupplierId() != null) {
            Suppliers supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier ID không tồn tại"));
            product.setSupplier(supplier);
        }

        productRepository.save(product);
        return toDto(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Products existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        modelMapper.map(productDTO, existingProduct);
        Products save = productRepository.save(existingProduct);
        return toDto(save);
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm để xóa");
        }
        productRepository.deleteById(id);
    }

    private ProductDTO toDto(Products product) {
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        dto.setProductId(product.getId());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getId());
        }
        return dto;
    }

    @Cacheable(value = "products")
    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findByProductNameContaining(keyword)
                .stream()
                .map(this::toDto)
                .toList();
    }
}