package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.EmployeeDTO;
import com.ecommerce.vn.models.entitis.EmployeeTerritories;
import com.ecommerce.vn.models.entitis.Employees;
import com.ecommerce.vn.repositories.EmployeeRepository;
import com.ecommerce.vn.repositories.EmployeeTerritoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeTerritoriesRepository employeeTerritoriesRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::toDto).toList();
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        Employees employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên ID: " + id));
        return toDto(employee);
    }

    private EmployeeDTO toDto(Employees employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getId());
        dto.setLastName(employee.getLastName());
        dto.setFirstName(employee.getFirstName());
        dto.setTitle(employee.getTitle());
        dto.setTitleOfCourtesy(employee.getTitleOfCourtesy());
        dto.setBirthDate(employee.getBirthDate());
        dto.setHireDate(employee.getHireDate());
        dto.setAddress(employee.getAddress());
        dto.setCity(employee.getCity());
        dto.setRegion(employee.getRegion());
        dto.setPostalCode(employee.getPostalCode());
        dto.setCountry(employee.getCountry());
        dto.setHomePhone(employee.getHomePhone());
        dto.setExtension(employee.getExtension());
        dto.setReportsTo(employee.getReportsTo());
        List<String> territoryIds = employeeTerritoriesRepository.findByEmployeeIdWithTerritory(employee.getId())
                .stream()
                .map(et -> et.getTerritory().getId())
                .toList();
        dto.setTerritoryIds(territoryIds);
        return dto;
    }
}
