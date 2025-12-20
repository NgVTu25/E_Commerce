package com.ecommerce.vn.services;

import com.ecommerce.vn.models.entitis.OrderDetails;
import com.ecommerce.vn.models.entitis.Orders;
import com.ecommerce.vn.repositories.EmployeeRepository;
import com.ecommerce.vn.repositories.OrderDetailRepository;
import com.ecommerce.vn.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetails findOrderById(Integer id) {
        return orderDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }



}
