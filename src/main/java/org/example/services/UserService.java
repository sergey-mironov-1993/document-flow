package org.example.services;

import org.example.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudOperationService<UserDTO, Long>{
    @Override
    List<UserDTO> findAll();

    @Override
    UserDTO findOne(Long id);

    @Override
    Boolean save(UserDTO userDTO);

    @Override
    void update(Long id, UserDTO userDTO);

    @Override
    void delete(Long id);
}
