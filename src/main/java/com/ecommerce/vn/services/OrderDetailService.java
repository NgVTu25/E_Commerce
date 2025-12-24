package com.ecommerce.vn.services;

import com.ecommerce.vn.models.entitis.OrderDetails;
import com.ecommerce.vn.repositories.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetails findOrderById(Integer id) {
        return orderDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

}
