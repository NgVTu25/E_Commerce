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

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Orders>> getOrderByCustomerId(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderByCustomerId(id));
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrdersDTOs ordersDTOs) {
        Orders newOrder = orderService.createOrder(ordersDTOs);
        return ResponseEntity.ok(newOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody OrdersDTOs ordersDTOs) {
        return ResponseEntity.ok(orderService.updateOrder(id, ordersDTOs));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
}