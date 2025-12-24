package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.CustomerDTO;
import com.ecommerce.vn.models.entitis.Customers;
import com.ecommerce.vn.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public List<CustomerDTO> getAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CustomerDTO.class))
                .toList();
    }

    public CustomerDTO getCustomerById(String id) {
        Customers customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CustomerID"));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customers entity = modelMapper.map(dto, Customers.class);
        customerRepository.save(entity);
        return dto;
    }

    public CustomerDTO updateCustomer(String id, CustomerDTO dto) {
        Customers existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Customer để update"));

        modelMapper.map(dto, existing);
        customerRepository.save(existing);

        return dto;
    }

    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy Customer để xóa");
        }
        customerRepository.deleteById(id);
    }
}

