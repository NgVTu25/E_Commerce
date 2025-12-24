package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.OrderDTO;
import com.ecommerce.vn.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDTO>> getOrderByCustomerId(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderByCustomerId(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(201).body(orderService.createOrder(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}