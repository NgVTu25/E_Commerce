package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.CustomerDTO;
import com.ecommerce.vn.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO dto) {
        return ResponseEntity.status(201).body(customerService.createCustomer(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable String id,
            @RequestBody CustomerDTO dto
    ) {
        return ResponseEntity.ok(customerService.updateCustomer(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
