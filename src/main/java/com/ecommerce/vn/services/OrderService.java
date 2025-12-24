package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.OrderDetailDTO;
import com.ecommerce.vn.dtos.OrderDTO;
import com.ecommerce.vn.models.entitis.*;
import com.ecommerce.vn.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> modelMapper.map(o, OrderDTO.class)).toList();
    }

    public OrderDTO getOrderById(Integer id) {
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderID"));
        return modelMapper.map(orders, OrderDTO.class);
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Orders orders = modelMapper.map(orderDTO, Orders.class);
        if (orderDTO.getCustomerId() != null) {
            Customers customer = customerRepository.findById(orderDTO.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            orders.setCustomer(customer);
        }

        if (orderDTO.getEmployeeId() != null) {
            Employees employee = employeeRepository.findById(orderDTO.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            orders.setEmployee(employee);
        }

        if (orderDTO.getShipVia() != null) {
            Shippers shipper = shipperRepository.findById(orderDTO.getShipVia())
                    .orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
            orders.setShipper(shipper);
        }

        Orders savedOrder = orderRepository.save(orders);

        if (orderDTO.getOrderDetails() != null && !orderDTO.getOrderDetails().isEmpty()) {
            List<OrderDetails> detailsList = new ArrayList<>();

            for (OrderDetailDTO itemDTO : orderDTO.getOrderDetails()) {
                Products product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + itemDTO.getProductId()));

                OrderDetails.OrderDetailsId detailId = new OrderDetails.OrderDetailsId(savedOrder.getId(), product.getId());

                OrderDetails detail = OrderDetails.builder()
                        .id(detailId)
                        .order(savedOrder)
                        .product(product)
                        .unitPrice(itemDTO.getUnitPrice() != null ? itemDTO.getUnitPrice() : product.getUnitPrice())
                        .quantity(itemDTO.getQuantity())
                        .discount(itemDTO.getDiscount() != null ? itemDTO.getDiscount() : 0)
                        .build();

                detailsList.add(detail);
            }
            orderDetailRepository.saveAll(detailsList);
        }
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public OrderDTO updateOrder(Integer id, OrderDTO orderDTO) {
        Orders exitingOrders = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        modelMapper.map(orderDTO, exitingOrders);
        Orders saveOrder = orderRepository.save(exitingOrders);
        return modelMapper.map(saveOrder, OrderDTO.class);
    }


    public List<OrderDTO> getOrderByCustomerId(String id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Orders> orders = orderRepository.findByCustomerId(id);

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy order để xóa");
        }
        orderRepository.deleteById(id);
    }
}
