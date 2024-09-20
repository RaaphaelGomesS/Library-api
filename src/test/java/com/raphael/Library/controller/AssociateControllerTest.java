package com.raphael.Library.controller;

import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.AuthenticationService;
import mock.entities.AssociateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AssociateControllerTest {

    @InjectMocks
    private AssociateController associateController;

    @Mock
    private AssociateService service;

    @Mock
    private AssociateRepository repository;

    @Mock
    private AuthenticationService authenticationService;

    private List<Associate> associates;

    private Associate associate;

    private AssociateResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        associates = AssociateMock.toList();
    }

    @Test
    void shouldGetAllAssociate() {
        assertDoesNotThrow(() -> {

            ResponseEntity<List<Associate>> responseEntity = ResponseEntity.ok(associates);

            when(repository.findAll()).thenReturn(associates);

            ResponseEntity<List<Associate>> result = associateController.getAllAssociate();

            assertEquals(responseEntity, result);
        });
    }
}
