package org.example.controller;

import org.example.dto.AuthDTO;
import org.example.dto.UserDTO;
import org.example.services.impl.AuthenticationService;
import org.example.services.impl.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAndRegController {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<String> performRegistration(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(registrationService.performRegistration(userDTO));
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthDTO authDTO) {
        return authenticationService.performLogin(authDTO);
    }
}
