package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.ShipperDTO;
import com.ecommerce.vn.services.ShipperService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipper")
public class ShipperController {
    private final ShipperService shipperService;

    @GetMapping
    public ResponseEntity<List<ShipperDTO>> getAllShippers() {
        return ResponseEntity.ok(shipperService.getAllShippers());
    }

    @PostMapping
    public ResponseEntity<ShipperDTO> createShipper(@Valid @RequestBody ShipperDTO shipperDTO) {
        return ResponseEntity.ok(shipperService.createShipper(shipperDTO));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<ShipperDTO> getShipperByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(shipperService.getShipperByPhone(phone));
    }

}
