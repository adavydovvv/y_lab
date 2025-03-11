package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ylab.dto.OrderDTO;
import ru.ylab.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "car", target = "car")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "descriptionOfTheService", target = "descriptionOfTheService")
    @Mapping(source = "date", target = "date")
    OrderDTO orderToOrderDTO(Order order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "car", target = "car")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "descriptionOfTheService", target = "descriptionOfTheService")
    @Mapping(source = "date", target = "date")
    Order orderDTOToOrder(OrderDTO orderDTO);
}
