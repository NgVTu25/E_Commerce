package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.ShipperDTO;
import com.ecommerce.vn.services.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
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
    public ResponseEntity<ShipperDTO> createShipper(@RequestBody ShipperDTO shipperDTO) {
        return ResponseEntity.ok(shipperService.createShipper(shipperDTO));
    }

}
