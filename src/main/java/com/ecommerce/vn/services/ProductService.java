package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.ProductsDTOs;
import com.ecommerce.vn.models.entitis.Products;
import com.ecommerce.vn.models.entitis.Categories;
import com.ecommerce.vn.models.entitis.Suppliers;
import com.ecommerce.vn.repositories.ProductRepository;
import com.ecommerce.vn.repositories.CategoryRepository;
import com.ecommerce.vn.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
    }

    public Products createProduct(ProductsDTOs productDTO) {
        Products product = new Products();
        product.setProductName(productDTO.getProductName());
        product.setQuantityPerUnit(productDTO.getQuantityPerUnit());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setUnitsInStock(productDTO.getUnitsInStock());
        product.setUnitsOnOrder(productDTO.getUnitsOnOrder());
        product.setReorderLevel(productDTO.getReorderLevel());
        product.setDiscontinued(productDTO.getDiscontinued());

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

        return productRepository.save(product);
    }

    public Products updateProduct(Long id, ProductsDTOs productDTO) {
        Products existingProduct = getProductById(id);
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setUnitPrice(productDTO.getUnitPrice());

        if (productDTO.getCategoryId() != null) {
            Categories category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category ID không tồn tại"));
            existingProduct.setCategory(category);
        }

        if (productDTO.getSupplierId() != null) {
            Suppliers supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier ID không tồn tại"));
            existingProduct.setSupplier(supplier);
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm để xóa");
        }
        productRepository.deleteById(id);
    }

    public List<Products> searchProducts(String keyword) {
        return productRepository.findByProductNameContaining(keyword);
    }
}