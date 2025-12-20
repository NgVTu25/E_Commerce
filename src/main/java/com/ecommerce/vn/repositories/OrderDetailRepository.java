package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {
}
