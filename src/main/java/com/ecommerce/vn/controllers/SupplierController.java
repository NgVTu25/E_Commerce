package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.SupplyDTO;
import com.ecommerce.vn.services.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplyDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyDTO> getSupplierById(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.getSuppliersById(id));
    }

    @PostMapping
    public ResponseEntity<SupplyDTO> createSupplier(@Valid @RequestBody SupplyDTO supplyDTO) {
        return ResponseEntity.ok(supplierService.createSuppliers(supplyDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplyDTO> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplyDTO supplyDTO) {
        return ResponseEntity.ok(supplierService.updateSuppliers(id, supplyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSuppliers(id);
        return ResponseEntity.ok("Đã xóa nhà cung cấp thành công");
    }
}