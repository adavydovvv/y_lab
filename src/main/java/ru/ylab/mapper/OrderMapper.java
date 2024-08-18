package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.OrderDTO;
import ru.ylab.model.Order;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "customer.userId", target = "customerId")
    @Mapping(source = "car.id", target = "carId")
    OrderDTO orderToOrderDTO(Order order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "customerId", target = "customer.userId")
    @Mapping(source = "carId", target = "car.id")
    Order orderDTOToOrder(OrderDTO orderDTO);
}
