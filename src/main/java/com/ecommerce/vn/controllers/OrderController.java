package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.OrderDTO;
import com.ecommerce.vn.models.entitis.OrderDetails;
import com.ecommerce.vn.services.OrderDetailService;
import com.ecommerce.vn.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

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
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(201).body(orderService.createOrder(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Integer id, @Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDTO));
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}