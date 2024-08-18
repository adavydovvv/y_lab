package ru.ylab.validators;

import ru.ylab.dto.OrderDTO;
import ru.ylab.utils.ValidationException;
import ru.ylab.utils.Validator;

public class OrderDTOValidator implements Validator<OrderDTO> {

    @Override
    public boolean validate(OrderDTO orderDTO) throws ValidationException {
        if (orderDTO == null) {
            throw new ValidationException("OrderDTO cannot be null");
        }

        if (orderDTO.getCustomerId() <= 0) {
            throw new ValidationException("Valid Customer ID is mandatory");
        }

        if (orderDTO.getCarId() <= 0) {
            throw new ValidationException("Valid Car ID is mandatory");
        }

        if (orderDTO.getPrice() < 0) {
            throw new ValidationException("Price cannot be negative");
        }

        return true;
    }
}
