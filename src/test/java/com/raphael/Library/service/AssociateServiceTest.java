package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import mock.entities.AssociateMock;
import mock.request.AssociateRequestDTOMock;
import mock.response.AssociateResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AssociateServiceTest {

    @InjectMocks
    private AssociateService associateService;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private AssociateBuilder associateBuilder;

    private Associate associate;

    private AssociateRequestDTO associateRequestDTO;

    private AssociateResponseDTO associateResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        associate = AssociateMock.toEntity();
        associateRequestDTO = AssociateRequestDTOMock.toRequestDTO();
        associateResponseDTO = AssociateResponseDTOMock.toResponseDTO();
    }

    @Test
    void shouldCreateAssociate() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findByUsername(any())).thenReturn(Optional.empty());
            when(associateBuilder.from(any())).thenReturn(associate);

            AssociateResponseDTO result = associateService.createAssociate(associateRequestDTO);

            verify(associateRepository, times(1)).save(any());

            assertNotNull(result);
            assertEquals(associateResponseDTO.getId(), result.getId());
        });
    }

    @Test
    void shouldThrowExceptionWhenCreateAssociateWithAlreadyUsernameRegister() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(associateRepository.findByUsername(any())).thenReturn(Optional.of(associate));

            associateService.createAssociate(associateRequestDTO);

            verify(associateRepository, times(0)).save(any());
        });

        Exception expectedException = new AssociateException("O username já está em uso.", HttpStatus.CONFLICT);

        assertTrue(actualException instanceof AssociateException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldUpdateAssociate() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findById(any())).thenReturn(Optional.of(associate));

            AssociateResponseDTO result = associateService.updateAssociate(associateRequestDTO, associate);

            verify(associateBuilder, times(1)).fromAssociate(any(), any());
            verify(associateRepository, times(1)).save(any());

            assertNotNull(result);
            assertEquals(associateResponseDTO.getId(), result.getId());
        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindAssociateToUpdate() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(associateRepository.findById(any())).thenReturn(Optional.empty());

            associateService.updateAssociate(associateRequestDTO, associate);

        });

        Exception expectedException = new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND);

        assertTrue(actualException instanceof AssociateException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldDeleteAssociate() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findById(any())).thenReturn(Optional.of(associate));

            associateService.deleteAssociate(1L, associate);

            verify(associateRepository, times(1)).delete(any());
        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindAssociateToDelete() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(associateRepository.findById(any())).thenReturn(Optional.empty());

            associateService.deleteAssociate(1L, associate);

        });

        Exception expectedException = new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND);

        assertTrue(actualException instanceof AssociateException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetById() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findById(any())).thenReturn(Optional.of(associate));

            associateService.getById(1L, associate);

        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindById() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(associateRepository.findById(any())).thenReturn(Optional.empty());

            associateService.getById(1L, associate);

        });

        Exception expectedException = new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND);

        assertTrue(actualException instanceof AssociateException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetByUsername() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findByUsername(any())).thenReturn(Optional.of(associate));

            associateService.getByUsername("Raphael", associate);

        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindByUsername() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(associateRepository.findByUsername(any())).thenReturn(Optional.empty());

            associateService.getByUsername("Teste", associate);

        });

        Exception expectedException = new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND);

        assertTrue(actualException instanceof AssociateException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }
}
