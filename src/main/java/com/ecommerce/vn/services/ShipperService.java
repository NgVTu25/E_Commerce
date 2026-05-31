package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.ShipperDTO;
import com.ecommerce.vn.models.entitis.Shippers;
import com.ecommerce.vn.repositories.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperService {
    private final ShipperRepository shipperRepository;
    private final ModelMapper modelMapper;

    public List<ShipperDTO> getAllShippers() {
        return shipperRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ShipperDTO toDto(Shippers shipper) {
        ShipperDTO dto = modelMapper.map(shipper, ShipperDTO.class);
        dto.setShipperId(shipper.getId());
        return dto;
    }

    public ShipperDTO createShipper(ShipperDTO shipperDTO) {
        Shippers shippers = modelMapper.map(shipperDTO, Shippers.class);
        Shippers saved = shipperRepository.save(shippers);
        return toDto(saved);
    }

    public ShipperDTO getShipperByPhone(String phone) {
        Shippers shippers = shipperRepository.findByPhone(phone);
        return toDto(shippers);
    }

}
