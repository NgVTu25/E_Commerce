package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.TerritoryDTO;
import com.ecommerce.vn.dtos.TerritoryEmployeeDTO;
import com.ecommerce.vn.models.entitis.EmployeeTerritories;
import com.ecommerce.vn.models.entitis.Territories;
import com.ecommerce.vn.repositories.EmployeeTerritoriesRepository;
import com.ecommerce.vn.repositories.TerritoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final EmployeeTerritoriesRepository employeeTerritoriesRepository;

    public List<TerritoryDTO> getAllTerritories() {
        return territoryRepository.findAllWithRegion().stream().map(this::toSummaryDto).toList();
    }

    public TerritoryDTO getTerritoryById(String id) {
        Territories territory = territoryRepository.findByIdWithRegion(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy territory: " + id));
        return toDetailDto(territory);
    }

    private TerritoryDTO toSummaryDto(Territories territory) {
        TerritoryDTO dto = new TerritoryDTO();
        dto.setTerritoryId(territory.getId());
        dto.setTerritoryDescription(territory.getTerritoryDescription());
        if (territory.getRegion() != null) {
            dto.setRegionId(territory.getRegion().getId());
            dto.setRegionDescription(territory.getRegion().getRegionDescription());
        }
        return dto;
    }

    private TerritoryDTO toDetailDto(Territories territory) {
        TerritoryDTO dto = toSummaryDto(territory);
        List<TerritoryEmployeeDTO> employees = employeeTerritoriesRepository.findByTerritoryIdWithEmployee(territory.getId())
                .stream()
                .map(this::toEmployeeSummary)
                .toList();
        dto.setEmployees(employees);
        return dto;
    }

    private TerritoryEmployeeDTO toEmployeeSummary(EmployeeTerritories et) {
        TerritoryEmployeeDTO dto = new TerritoryEmployeeDTO();
        var emp = et.getEmployee();
        dto.setEmployeeId(emp.getId());
        dto.setFullName(emp.getFirstName() + " " + emp.getLastName());
        dto.setTitle(emp.getTitle());
        dto.setCity(emp.getCity());
        return dto;
    }
}
