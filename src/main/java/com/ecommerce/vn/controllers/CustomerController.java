package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.CustomersDTOs;
import com.ecommerce.vn.models.entitis.Customers;
import com.ecommerce.vn.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    //Method: GET
    @GetMapping
    public ResponseEntity<List<Customers>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Customers> createCustomer(@RequestBody CustomersDTOs customersDTOs) {
        Customers newCustomer = customerService.createCustomer(customersDTOs);
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customers> updateCustomer(@PathVariable String id, @RequestBody CustomersDTOs customersDTOs) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customersDTOs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
       return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
