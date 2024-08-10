package com.raphael.Library.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.raphael.Library.dto.login.LoginResponse;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final AssociateRepository associateRepository;

    @Value("${api.token.secret}")
    private String secret;

    public String validateToken(String token) throws AssociateException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("Library_api")
                    .build()
                    .verify(token).getSubject();

        } catch (JWTVerificationException exception) {
            throw new AssociateException("The token is invalid or expired!", HttpStatus.BAD_REQUEST);
        }
    }

    public LoginResponse generateToken(Associate associate) throws AssociateException {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("Library_api")
                    .withSubject(associate.getAssociateId().toString())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);

            return new LoginResponse(token, getExpirationDate());

        } catch (JWTCreationException exception) {
            throw new AssociateException("Error when create token!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return associateRepository.findByUsername(username).orElse(null);
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2L).toInstant(ZoneOffset.of("-03:00"));
    }
}
