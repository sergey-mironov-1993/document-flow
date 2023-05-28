package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDTO;
import org.example.mappers.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.utils.AnotherUtils.THE_USER_IS_REGISTERED;
import static org.example.utils.AnotherUtils.THE_USER_IS_ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public String performRegistration(UserDTO userDTO) {
        Boolean existsUser = userService.existsUserByLogin(userDTO.getLogin());
        if (existsUser)
            return THE_USER_IS_ALREADY_REGISTERED;
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userMapper.toEntity(userDTO));
        return THE_USER_IS_REGISTERED;
    }
}
