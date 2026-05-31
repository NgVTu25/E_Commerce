package com.ecommerce.vn.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TerritoryDTO {
    private String territoryId;
    private String territoryDescription;
    private Long regionId;
    private String regionDescription;
    private List<TerritoryEmployeeDTO> employees;
}
