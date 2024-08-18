package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.CarDTO;
import ru.ylab.model.Car;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "id", target = "id")
    CarDTO carToCarDTO(Car car);

    @Mapping(source = "id", target = "id")
    Car carDTOToCar(CarDTO carDTO);
}
