    package ru.ylab.mapper;

    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import ru.ylab.dto.UserDTO;
    import ru.ylab.model.User;

    @Mapper(componentModel = "spring")
    public interface UserMapper {
        @Mapping(source = "userId", target = "id")
        @Mapping(source = "phone", target = "phone")
        UserDTO userToUserDTO(User user);

        @Mapping(source = "id", target = "userId")
        @Mapping(source = "phone", target = "phone")
        User userDTOToUser(UserDTO userDTO);
    }
