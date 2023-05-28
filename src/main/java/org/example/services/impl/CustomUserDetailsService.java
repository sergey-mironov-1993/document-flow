package org.example.services.impl;

import org.example.models.User;
import org.example.details.CustomUserDetails;
import org.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.utils.AnotherUtils.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> registeredUser = userRepository.findByLogin(login);
        if (registeredUser.isEmpty())
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        return new CustomUserDetails(registeredUser.get());
    }
}
