package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByCustomerId(String id);
}

