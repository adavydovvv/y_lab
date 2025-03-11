package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ylab.dto.CarDTO;
import ru.ylab.model.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(source = "id", target = "id")
    CarDTO carToCarDTO(Car car);

    @Mapping(source = "id", target = "id")
    Car carDTOToCar(CarDTO carDTO);
}
