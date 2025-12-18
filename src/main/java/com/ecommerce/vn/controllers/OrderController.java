package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.OrdersDTOs;
import com.ecommerce.vn.models.entitis.Orders;
import com.ecommerce.vn.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Method: GET
    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Method: GET
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Method: POST
    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrdersDTOs ordersDTOs) {
        Orders newOrder = orderService.createOrder(ordersDTOs);
        return ResponseEntity.ok(newOrder);
    }
}