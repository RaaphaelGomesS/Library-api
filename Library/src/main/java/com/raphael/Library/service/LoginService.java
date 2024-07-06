package com.raphael.Library.service;

import com.raphael.Library.dto.LoginRequest;
import com.raphael.Library.dto.LoginResponse;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtEncoder jwtEncoder;

    private final PasswordEncoder passwordEncoder;

    private final AssociateRepository associateRepository;

    private final long EXPIRES_IN = 500L;

    public LoginResponse getLoginToken(LoginRequest loginRequest) throws AssociateException {

        Associate associate = associateRepository.findByUsername(loginRequest.username()).orElse(null);

        if (associate == null) {
            throw new AssociateException("O username nâo foi encotrado!", HttpStatus.NOT_FOUND);
        }

        isLoginCorrect(loginRequest, associate);

        return generateToken(associate.getAssociateId(), associate.getRole().name());
    }

    private void isLoginCorrect(LoginRequest loginRequest, Associate associate) throws AssociateException {

        if (!(passwordEncoder.matches(loginRequest.password(), associate.getPassword()))) {
            throw new AssociateException("A senha está errada!", HttpStatus.BAD_REQUEST);
        }
    }

    private LoginResponse generateToken(Long userId, String role) {

        JwtClaimsSet params = JwtClaimsSet
                .builder()
                .issuer("Library")
                .subject(userId.toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_IN))
                .claim("role", role)
                .build();

        String jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(params)).getTokenValue();

        return new LoginResponse(jwtToken, EXPIRES_IN);
    }
}
