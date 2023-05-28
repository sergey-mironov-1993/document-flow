package org.example.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.UserNotFoundException;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.example.utils.AnotherUtils.*;

@Component
@Transactional
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${key.jwt_secret}")
    private String secret;
    private final UserRepository userRepository;

    public String generateToken(String login) {
        var user = userRepository.findByLogin(login).orElseThrow(UserNotFoundException::new);
            Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(20).toInstant());
            return JWT.create()
                    .withClaim(LOGIN_CLAIM, login)
                    .withClaim(ID_CLAIM, user.getId())
                    .withClaim(EMAIL_CLAIM, user.getEmail())
                    .withClaim(FIRST_NAME_CLAIM, user.getFirstName())
                    .withClaim(LAST_NAME_CLAIM, user.getLastName())
                    .withClaim(PATRONYMIC_CLAIM, user.getPatronymic())
                    .withClaim(ROLE_CLAIM, String.valueOf(user.getRole()))
                    .withIssuedAt(new Date())
                    .withExpiresAt(expirationDate)
                    .sign(Algorithm.HMAC256(secret));
    }
}
