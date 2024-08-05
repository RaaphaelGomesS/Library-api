package com.raphael.Library.controller;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.dto.AssociateResponseDTO;
import com.raphael.Library.dto.LoginRequest;
import com.raphael.Library.dto.LoginResponse;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final AssociateService service;

    @PostMapping("/")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AssociateException {

        LoginResponse loginResponse = loginService.getLoginToken(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AssociateResponseDTO> register(@RequestBody AssociateRequestDTO associateRequestDTO) throws AssociateException {

        AssociateResponseDTO response = service.createAssociate(associateRequestDTO);

        return ResponseEntity.ok(response);
    }
}
