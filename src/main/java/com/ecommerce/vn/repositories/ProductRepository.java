package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByProductNameContaining(String ProductName);

}
