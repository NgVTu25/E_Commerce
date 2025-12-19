package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.SuppliersDTOs;
import com.ecommerce.vn.models.entitis.Suppliers;
import com.ecommerce.vn.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public List<Suppliers> getAllSuppliers(){
        return supplierRepository.findAll();
    }

    public Suppliers getSuppliersById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy Supplier"));
    }

    public Suppliers createSuppliers(SuppliersDTOs suppliersDTOs) {
        Suppliers suppliers = new Suppliers();
        modelMapper.map(suppliersDTOs, suppliers);
        return supplierRepository.save(suppliers);
    }

    public Suppliers updateSuppliers(Long id, SuppliersDTOs suppliersDTOs) {
        Suppliers suppliers = getSuppliersById(id);
        modelMapper.map(suppliersDTOs, suppliers);
        return supplierRepository.save(suppliers);
    }

    public void deleteSuppliers(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm để xóa");
        }
        supplierRepository.deleteById(id);
    }
}
