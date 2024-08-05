package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.dto.AssociateResponseDTO;
import com.raphael.Library.dto.LoginRequest;
import com.raphael.Library.dto.LoginResponse;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    private final AssociateService service;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AssociateException {

        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

        Authentication auth = authenticationManager.authenticate(usernamePassword);

        LoginResponse loginResponse = authenticationService.generateToken((Associate) auth.getPrincipal());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AssociateResponseDTO> register(@RequestBody AssociateRequestDTO associateRequestDTO) throws AssociateException {

        AssociateResponseDTO response = service.createAssociate(associateRequestDTO);

        return ResponseEntity.ok(response);
    }
}
