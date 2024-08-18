package ru.ylab.validators;

import ru.ylab.dto.UserDTO;
import ru.ylab.utils.ValidationException;
import ru.ylab.utils.Validator;

public class UserDTOValidator implements Validator<UserDTO> {

    @Override
    public boolean validate(UserDTO userDTO) throws ValidationException {
        if (userDTO == null) {
            throw new ValidationException("UserDTO cannot be null");
        }

        if (isBlank(userDTO.getUsername())) {
            throw new ValidationException("Username is mandatory");
        }

        if (isBlank(userDTO.getPassword())) {
            throw new ValidationException("Password is mandatory");
        }

        if (isBlank(userDTO.getFirstName())) {
            throw new ValidationException("First name is mandatory");
        }

        if (isBlank(userDTO.getLastName())) {
            throw new ValidationException("Last name is mandatory");
        }

        if (userDTO.getRole() == null) {
            throw new ValidationException("Role is mandatory");
        }

        if (userDTO.getEmail() != null && !isValidEmail(userDTO.getEmail())) {
            throw new ValidationException("Email should be valid");
        }
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}
