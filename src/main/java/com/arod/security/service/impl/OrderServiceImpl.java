package com.arod.security.service.impl;

import com.arod.security.dto.OrderLineDTO;
import com.arod.security.dto.request.OrderRequestDTO;
import com.arod.security.dto.response.OrderResponseDTO;
import com.arod.security.mapper.OrderMapper;
import com.arod.security.persistence.entity.Order;
import com.arod.security.persistence.entity.OrderLine;
import com.arod.security.persistence.repository.OrderRepository;
import com.arod.security.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<OrderResponseDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public List<OrderResponseDTO> findByVendorID(Long vendorID) {
        return repository.findByVendorID(vendorID)
                .stream().map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO request) {
        Order order = mapper.toOrder(request);
        return mapper.toDTO(repository.save(order));
    }

    @Override
    public Optional<OrderResponseDTO> update(Long id, OrderRequestDTO request) {
        Optional<Order> oOrder = repository.findById(id);

        if (oOrder.isEmpty())
            return Optional.empty();

        Order order = oOrder.get();
        mapper.updateValues(request, order);

        order.getLines().removeIf(line -> !filter(line, request.getOrderLines()));

        return Optional.of(repository.save(order))
                .map(mapper::toDTO);
    }

    protected boolean filter(OrderLine line, List<OrderLineDTO> linesDTO) {
        return linesDTO.stream()
                .anyMatch(lineDTO -> line.getId() != null
                        && line.getId().getProduct() != null
                        && line.getId().getProduct().getId() != null
                        && line.getId().getProduct().getId().equals(lineDTO.getProductID()));
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id)
                .map(order -> {
                    repository.delete(order);
                    return true;
                }).isPresent();
    }

    private final OrderRepository repository;
    private final OrderMapper mapper;
}
