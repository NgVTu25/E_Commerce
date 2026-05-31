package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.TerritoryDTO;
import com.ecommerce.vn.services.TerritoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/territory")
@RequiredArgsConstructor
public class TerritoryController {

    private final TerritoryService territoryService;

    @GetMapping
    public ResponseEntity<List<TerritoryDTO>> getAllTerritories() {
        return ResponseEntity.ok(territoryService.getAllTerritories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerritoryDTO> getTerritoryById(@PathVariable String id) {
        return ResponseEntity.ok(territoryService.getTerritoryById(id));
    }
}
