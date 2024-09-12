package com.raphael.Library.service;

import com.raphael.Library.dto.requisition.RequisitionRequestDTO;
import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Book;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.repository.RequisitionRepository;
import mock.entities.AssociateMock;
import mock.entities.BookMock;
import mock.entities.RequisitionMock;
import mock.request.RequisitionRequestDTOMock;
import mock.response.RequisitionResponseDTOMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RequisitionServiceTest {

    @InjectMocks
    private RequisitionService requisitionService;

    @Mock
    private RequisitionRepository requisitionRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private AssociateService associateService;

    private Associate associate;

    private RequisitionRequestDTO requestDTO;

    private Page<Associate> associates;

    private Book book;

    private Requisition requisition;

    private RequisitionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book = BookMock.toEntity();
        associates = AssociateMock.toPage();
        associate = AssociateMock.toEntity();
        requisition = RequisitionMock.toEntity();
        requestDTO = RequisitionRequestDTOMock.toRequest();
        responseDTO = RequisitionResponseDTOMock.toResponseDTO();
    }

    @Test
    void shouldMakeRequisitionToCreate() {
        assertDoesNotThrow(() -> {

            when(associateService.getByUsername(any(), any())).thenReturn(associate);
            when(bookRepository.findByBookName(any())).thenReturn(Optional.of(book));

            RequisitionResponseDTO result = requisitionService.makeRequisitionByAction(requestDTO, associate);

            verify(requisitionRepository, times(1)).save(any());
            verify(associateRepository, times(1)).save(any());

            assertEquals(responseDTO.getRequisitionId(), result.getRequisitionId());

        });
    }
}
