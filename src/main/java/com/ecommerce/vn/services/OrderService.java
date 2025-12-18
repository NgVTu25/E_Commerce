package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.OrdersDTOs;
import com.ecommerce.vn.models.entitis.*;
import com.ecommerce.vn.repositories.CustomerRepository;
import com.ecommerce.vn.repositories.EmployeeRepository;
import com.ecommerce.vn.repositories.OrderRepository;
import com.ecommerce.vn.repositories.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final ModelMapper modelMapper;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderID"));
    }

    public Orders createOrder(OrdersDTOs ordersDTOs) {
        Orders orders = new Orders();
        modelMapper.map(ordersDTOs, orders);
        if (ordersDTOs.getCustomerId() != null) {
            Customers customer = customerRepository.findById(ordersDTOs.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            orders.setCustomer(customer);
        }

        if (ordersDTOs.getEmployeeId() != null) {
            Employees employee = employeeRepository.findById(ordersDTOs.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            orders.setEmployee(employee);
        }

        if (ordersDTOs.getShipVia() != null) {
            Shippers shipper = shipperRepository.findById(ordersDTOs.getShipVia())
                    .orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
            orders.setShipper(shipper);
        }

        return orderRepository.save(orders);
    }

    public Orders updateOrder(Integer id, OrdersDTOs ordersDTOs) {
        Orders exitingOrders = getOrderById(id);
        modelMapper.map(exitingOrders, ordersDTOs);
        if (ordersDTOs.getCustomerId() != null) {
            Customers customer = customerRepository.findById(ordersDTOs.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            exitingOrders.setCustomer(customer);
        }

        if (ordersDTOs.getEmployeeId() != null) {
            Employees employee = employeeRepository.findById(ordersDTOs.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            exitingOrders.setEmployee(employee);
        }

        if (ordersDTOs.getShipVia() != null) {
            Shippers shipper = shipperRepository.findById(ordersDTOs.getShipVia())
                    .orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
            exitingOrders.setShipper(shipper);
        }
        return orderRepository.save(exitingOrders);
    }

    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy order để xóa");
        }
        orderRepository.deleteById(id);
    }

}
