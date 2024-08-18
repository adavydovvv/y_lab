package ru.ylab.validators;

import ru.ylab.dto.CarDTO;
import ru.ylab.utils.ValidationException;
import ru.ylab.utils.Validator;

public class CarDTOValidator implements Validator<CarDTO> {

    @Override
    public boolean validate(CarDTO carDTO) throws ValidationException {
        if (carDTO == null) {
            throw new ValidationException("CarDTO cannot be null");
        }

        if (isBlank(carDTO.getBrand())) {
            throw new ValidationException("Brand is mandatory");
        }

        if (isBlank(carDTO.getModel())) {
            throw new ValidationException("Model is mandatory");
        }

        if (carDTO.getYear() <= 0) {
            throw new ValidationException("Valid Year is mandatory");
        }

        if (carDTO.getPrice() <= 0) {
            throw new ValidationException("Valid Price is mandatory");
        }

        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
