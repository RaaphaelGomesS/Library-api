package com.raphael.Library.controller;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.dto.login.LoginRequest;
import com.raphael.Library.dto.login.LoginResponse;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.AuthenticationService;
import mock.request.AssociateRequestDTOMock;
import mock.response.AssociateResponseDTOMock;
import mock.response.LoginResponseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AssociateService service;

    @Mock
    private AuthenticationManager authenticationManager;

    private LoginRequest loginRequest;

    private AssociateRequestDTO associateRequestDTO;

    private AssociateResponseDTO response;

    private Authentication auth;

    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        auth = mock(Authentication.class);
        loginResponse = LoginResponseMock.toResponse();
        response = AssociateResponseDTOMock.toResponseDTO();
        associateRequestDTO = AssociateRequestDTOMock.toRequestDTO();
        loginRequest = new LoginRequest("test", "test");
    }

    @Test
    void shouldLogin() {
        assertDoesNotThrow(() -> {

            ResponseEntity<LoginResponse> responseEntity = ResponseEntity.ok(loginResponse);

            when(authenticationManager.authenticate(any())).thenReturn(auth);
            when(authenticationService.generateToken(any())).thenReturn(loginResponse);

            ResponseEntity<LoginResponse> result = loginController.login(loginRequest);

            assertEquals(responseEntity, result);

        });
    }

    @Test
    void shouldRegister() {
        assertDoesNotThrow(() -> {

            ResponseEntity<AssociateResponseDTO> responseEntity = ResponseEntity.ok(response);

            when(service.createAssociate(any())).thenReturn(response);

            ResponseEntity<AssociateResponseDTO> result = loginController.register(associateRequestDTO);

            assertEquals(responseEntity, result);

        });
    }
}
