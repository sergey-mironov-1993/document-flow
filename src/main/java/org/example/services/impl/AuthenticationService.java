package org.example.services.impl;

import org.example.dto.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.example.utils.AnotherUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.example.utils.AnotherUtils.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public Map<String, String> performLogin(AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(
                        authDTO.getLogin(),
                        authDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of(MESSAGE, INCORRECT_CREDENTIAL);
        }
        String token = jwtTokenService.generateToken(authDTO.getLogin());
        return Map.of(JWT_TOKEN, token);
    }
}
