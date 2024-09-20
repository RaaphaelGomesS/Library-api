package com.raphael.Library.service;

import com.raphael.Library.dto.login.LoginResponse;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.repository.AssociateRepository;
import mock.entities.AssociateMock;
import mock.response.LoginResponseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AssociateRepository associateRepository;

    private Associate associate;

    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        associate = AssociateMock.toEntity();
        loginResponse = LoginResponseMock.toResponse();

        ReflectionTestUtils.setField(authenticationService, "secret", "test");
    }

    @Test
    void shouldValidateToken() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findByUsername(any())).thenReturn(Optional.of(associate));

            Associate result = authenticationService.validateToken("test");

            assertEquals(associate, result);
        });
    }

    @Test
    void shouldGenerateToken() {
        assertDoesNotThrow(() -> {

            LoginResponse result = authenticationService.generateToken(associate);

            assertEquals(loginResponse, result);
        });
    }
}
