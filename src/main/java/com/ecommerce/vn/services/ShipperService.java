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
                .map(shippers -> modelMapper.map(shippers, ShipperDTO.class))
                .toList();
    }

    public ShipperDTO createShipper(ShipperDTO shipperDTO) {
        Shippers shippers = modelMapper.map(shipperDTO, Shippers.class);
        shipperRepository.save(shippers);
        return shipperDTO;
    }

    public ShipperDTO getShipperByPhone(String phone) {
        Shippers shippers = shipperRepository.findByPhone(phone);
        return modelMapper.map(shippers, ShipperDTO.class);
    }

}
