package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.SupplyDTO;
import com.ecommerce.vn.models.entitis.Suppliers;
import com.ecommerce.vn.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Cacheable(value = "suppliers")
    public List<SupplyDTO> getAllSuppliers(){
        return supplierRepository.findAll().
                stream().map(suppliers -> modelMapper.map(suppliers, SupplyDTO.class)).toList();
    }

    public SupplyDTO getSuppliersById(Integer id) {
        Suppliers supply= supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Supply"));
        return modelMapper.map(supply, SupplyDTO.class);
    }

    @CacheEvict(value = "suppliers",allEntries = true)
    public SupplyDTO createSuppliers(SupplyDTO supplyDTO) {
        Suppliers suppliers = modelMapper.map(supplyDTO, Suppliers.class);
        supplierRepository.save(suppliers);
        return supplyDTO;
    }

    @CacheEvict(value = "suppliers",allEntries = true)
    public SupplyDTO updateSuppliers(Integer id, SupplyDTO supplyDTO) {
        Suppliers existingSupply = modelMapper.map(getSuppliersById(id), Suppliers.class);
        modelMapper.map(supplyDTO, existingSupply);
        supplierRepository.save(existingSupply);
        return supplyDTO;
    }

    public void deleteSuppliers(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm để xóa");
        }
        supplierRepository.deleteById(id);
    }
}
