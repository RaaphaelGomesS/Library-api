package com.raphael.Library.service;

import com.raphael.Library.entities.Associate;
import com.raphael.Library.repository.AssociateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AssociateServiceTest {

    @InjectMocks
    private AssociateService associateService;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Associate associate;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should() {
        assertDoesNotThrow(() -> {

        });
    }
}
