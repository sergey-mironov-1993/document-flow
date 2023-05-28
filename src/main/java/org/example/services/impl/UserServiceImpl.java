package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDTO;
import org.example.exceptions.EmptyEntityDataException;
import org.example.exceptions.EntityAlreadyRemovedException;
import org.example.exceptions.NotFoundWithThisIdException;
import org.example.mappers.UserMapper;
import org.example.models.User;
import org.example.repository.UserRepository;
import org.example.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAll() {
        return userRepository
                .findByIsDeletedFalse()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDTO findOne(Long id) {
        return userRepository
                .findByIdAndIsDeletedFalse(id)
                .map(userMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public Boolean save(UserDTO userDTO) {
        var userForSave = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .patronymic(userDTO.getPatronymic())
                .login(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .telegram(userDTO.getTelegram())
                .role(userDTO.getRole())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(userForSave);
        return null;
    }

    @Override
    public void update(Long id, UserDTO userDTO) {
        if (!userRepository.existsUserById(id))
            throw new NotFoundWithThisIdException();
        if (hasUserDTOEmptyProperties(userDTO))
            throw new EmptyEntityDataException();
        userRepository.setNewEmailAndModifyDate(id, userDTO.getEmail(), LocalDateTime.now());
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsUserById(id))
            throw new NotFoundWithThisIdException();
        if (userRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        userRepository.setIsRemovedTrueAndRemoveDate(id, LocalDateTime.now());
    }

    public Boolean existsUserByTelegram(String telegram) {
        return userRepository.existsUserByTelegram(telegram);
    }

    public Boolean existsUserByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    public User findUserByLogin(String userLogin) {
        return userRepository.findByLogin(userLogin).get();
    }

    private Boolean hasUserDTOEmptyProperties(UserDTO userDTO) {
        return userDTO.getEmail() == null;
    }
}
