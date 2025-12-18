package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, String> {

}
