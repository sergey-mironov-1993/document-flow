package org.example.mappers;

import org.example.dto.UserDTO;
import org.example.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
