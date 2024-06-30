package com.raphael.Library.controller;

import com.raphael.Library.dto.LoginRequest;
import com.raphael.Library.dto.Loginresponse;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final JwtEncoder jwtEncoder;

    private final AssociateRepository associateRepository;

    @PostMapping("/")
    public ResponseEntity<List<Loginresponse>> login(@RequestBody LoginRequest loginRequest) {

        Optional<Associate> associate = associateRepository.findByUsername(loginRequest.username());

    }
}
