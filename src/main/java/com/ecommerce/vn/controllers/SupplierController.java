package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.SuppliersDTOs;
import com.ecommerce.vn.models.entitis.Suppliers;
import com.ecommerce.vn.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Suppliers>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suppliers> getSupplierById(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.getSuppliersById(id));
    }

    @PostMapping
    public ResponseEntity<Suppliers> createSupplier(@RequestBody SuppliersDTOs suppliersDTOs) {
        return ResponseEntity.ok(supplierService.createSuppliers(suppliersDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Suppliers> updateSupplier(@PathVariable Integer id, @RequestBody SuppliersDTOs suppliersDTOs) {
        return ResponseEntity.ok(supplierService.updateSuppliers(id, suppliersDTOs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSuppliers(id);
        return ResponseEntity.ok("Đã xóa nhà cung cấp thành công");
    }
}