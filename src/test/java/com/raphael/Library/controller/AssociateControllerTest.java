package com.raphael.Library.controller;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.service.AssociateService;
import com.raphael.Library.service.AuthenticationService;
import mock.entities.AssociateMock;
import mock.request.AssociateRequestDTOMock;
import mock.response.AssociateResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private AssociateRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        associates = AssociateMock.toList();
        associate = AssociateMock.toEntity();
        requestDTO = AssociateRequestDTOMock.toRequestDTO();
        responseDTO = AssociateResponseDTOMock.toResponseDTO();
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

    @Test
    void shouldThrowExceptionWhenHasNotAssociates() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(repository.findAll()).thenReturn(Collections.emptyList());

            associateController.getAllAssociate();

        });

        Exception expectedException = new AssociateException("Nenhum associado encontrado.", HttpStatus.NOT_FOUND);

        assertTrue(actualException instanceof AssociateException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetAssociate() {
        assertDoesNotThrow(() -> {

            when(authenticationService.validateToken(any())).thenReturn(associate);
            when(service.getById(anyLong(), any())).thenReturn(associate);

            ResponseEntity<AssociateResponseDTO> result = associateController.getAssociate(1L, "token");

            assertNotNull(result);
        });
    }

    @Test
    void shouldUpdateAssociate() {
        assertDoesNotThrow(() -> {

            ResponseEntity<AssociateResponseDTO> responseEntity = ResponseEntity.ok(responseDTO);

            when(authenticationService.validateToken(any())).thenReturn(associate);
            when(service.updateAssociate(any(), any())).thenReturn(responseDTO);

            ResponseEntity<AssociateResponseDTO> result = associateController.updateAssociate(requestDTO, "token");

            assertEquals(responseEntity, result);
        });
    }

    @Test
    void shouldDeleteAssociate() {
        assertDoesNotThrow(() -> {

            ResponseEntity<String> responseEntity = ResponseEntity.ok("Associado deletado, id: " + 1L);

            when(authenticationService.validateToken(any())).thenReturn(associate);

            ResponseEntity<String> result = associateController.deleteAssociate(1L, "token");

            verify(service, times(1)).deleteAssociate(anyLong(), any());

            assertEquals(responseEntity, result);
        });
    }
}
