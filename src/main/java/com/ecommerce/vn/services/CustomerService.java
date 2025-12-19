package com.ecommerce.vn.services;

import com.ecommerce.vn.config.GlobalExceptionHandler;
import com.ecommerce.vn.dtos.CustomersDTOs;
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

    public List<Customers> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customers getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CustomerID"));
    }

    public Customers createCustomer(CustomersDTOs customersDTOs) {
        Customers customers = new Customers();
        modelMapper.map(customersDTOs, customers);

        return customerRepository.save(customers);
    }

    public Customers updateCustomer(String id, CustomersDTOs customersDTOs) {
        Customers exitingCustomer = getCustomerById(id);
        modelMapper.map(customersDTOs, exitingCustomer);
        return customerRepository.save(exitingCustomer);
    }

    public String deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy Customer để xóa");
        }
            customerRepository.deleteById(id);
        return "Đã xóa thành công customer";
    }
}
