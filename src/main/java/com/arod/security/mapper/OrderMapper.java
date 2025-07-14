package com.arod.security.mapper;

import com.arod.security.dto.OrderLineDTO;
import com.arod.security.dto.request.OrderRequestDTO;
import com.arod.security.dto.response.OrderResponseDTO;
import com.arod.security.persistence.entity.*;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "quantity", target = "qty")
    @Mapping(source = "unitPrice", target = "price")
    OrderLine toOrderLine(OrderLineDTO dto);

    @AfterMapping
    default void afterMappingOrderLine(OrderLineDTO dto, @MappingTarget OrderLine line) {

        PKOrderLine pk = Optional.ofNullable(line.getId())
                        .orElseGet(PKOrderLine::new);

        if (pk.getProduct() == null || pk.getProduct().getId() == null
            || !pk.getProduct().getId().equals(dto.getProductID()))
        {
            Product product = new Product();
            product.setId(dto.getProductID());

            pk.setProduct(product);
        }

        line.setId(pk);
    }

    @InheritInverseConfiguration(name = "toOrderLine")
    @Mapping(source = "id.product.id", target = "productID")
    OrderLineDTO toDTO(OrderLine line);

    @InheritConfiguration(name = "toOrderLine")
    void updateValues(OrderLineDTO dto, @MappingTarget OrderLine orderLine);

    @Mapping(source = "date", target = "dateOrder")
    @Mapping(source = "description", target = "comment")
    @Mapping(source = "orderLines", target = "lines")
    Order toOrder(OrderRequestDTO request);

    @AfterMapping
    default void afterMapping(OrderRequestDTO request, @MappingTarget Order order) {

        if (order.getVendor() == null
            || order.getVendor().getId() == null
            || !order.getVendor().getId().equals(request.getVendorID()))
        {
            Vendor vendor = new Vendor();
            vendor.setId(request.getVendorID());

            order.setVendor(vendor);
        }

        order.getLines().forEach(line -> setOrder(line, order));
    }

    default void setOrder(OrderLine line, Order order) {
        PKOrderLine pk = Optional.ofNullable(line.getId())
                .orElseGet(PKOrderLine::new);

        pk.setOrder(order);

        line.setId(pk);
    }

    @InheritInverseConfiguration(name = "toOrder")
    OrderResponseDTO toDTO(Order order);

    @InheritConfiguration(name = "toOrder")
    void updateValues(OrderRequestDTO dto, @MappingTarget Order order);

    default void updateValues(List<OrderLineDTO> dtoLines, @MappingTarget List<OrderLine> lines) {
        dtoLines.forEach(dtoLine -> addLine(dtoLine, lines));
    }

    default void addLine(OrderLineDTO dtoLine, List<OrderLine> lines) {
        lines.stream()
                .filter(line -> filter(line, dtoLine.getProductID()))
                .findFirst()
                .ifPresentOrElse(line -> updateValues(dtoLine, line)
                    , () -> lines.add(toOrderLine(dtoLine)));
    }

    default boolean filter(OrderLine line, Long productID) {
        return Optional.of(line)
                .filter(orderLine -> orderLine.getId() != null && orderLine.getId().getProduct() != null
                    && orderLine.getId().getProduct().getId() != null
                    && orderLine.getId().getProduct().getId().equals(productID))
                .isPresent();
    }
}
