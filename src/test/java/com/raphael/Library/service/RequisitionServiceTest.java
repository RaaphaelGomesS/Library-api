package com.raphael.Library.service;

import com.raphael.Library.dto.requisition.RequisitionPageDTO;
import com.raphael.Library.dto.requisition.RequisitionRequestDTO;
import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Book;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.exception.RequisitionException;
import com.raphael.Library.indicator.StatusIndicator;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
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

            book.setBookId(2L);
            requestDTO.setAction(StatusIndicator.CRIADO.getAction());

            when(associateService.getByUsername(any(), any())).thenReturn(associate);
            when(bookRepository.findByBookName(any())).thenReturn(Optional.of(book));
            when(requisitionRepository.save(any())).thenReturn(requisition);

            RequisitionResponseDTO result = requisitionService.makeRequisitionByAction(requestDTO, associate);

            verify(requisitionRepository, times(1)).save(any());
            verify(associateRepository, times(1)).save(any());

            assertEquals(responseDTO.getRequisitionId(), result.getRequisitionId());
            assertEquals(responseDTO.getRetiredDate(), result.getRetiredDate());
            assertEquals(responseDTO.getDevolutionDate(), result.getDevolutionDate());
            assertEquals(StatusIndicator.CRIADO, result.getStatusIndicator());
        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindStatusIndicator() {
        Exception actualException = assertThrows(Exception.class, () -> {

            requestDTO.setAction("test");

            when(associateService.getByUsername(any(), any())).thenReturn(associate);

            requisitionService.makeRequisitionByAction(requestDTO, associate);
        });

        Exception expectedException = new RequisitionException("Não é possivel realizar essa ação, tente: abrir, renovar ou fechar.", HttpStatus.CONFLICT);

        assertInstanceOf(RequisitionException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCantFindBookToCreateRequisition() {
        Exception actualException = assertThrows(Exception.class, () -> {

            requestDTO.setAction(StatusIndicator.CRIADO.getAction());

            when(associateService.getByUsername(any(), any())).thenReturn(associate);
            when(bookRepository.findByBookName(any())).thenReturn(Optional.empty());

            requisitionService.makeRequisitionByAction(requestDTO, associate);
        });

         Exception expectedException = new BookException("Livro não encontrado.", HttpStatus.NOT_FOUND);

        assertInstanceOf(BookException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldMakeRequisitionToUpdate() {
        assertDoesNotThrow(() -> {

            requestDTO.setAction(StatusIndicator.POSTERGADO.getAction());

            when(requisitionRepository.findById(any())).thenReturn(Optional.of(requisition));
            when(requisitionRepository.save(any())).thenReturn(requisition);

            RequisitionResponseDTO result = requisitionService.makeRequisitionByAction(requestDTO, associate);

            assertEquals(responseDTO.getRequisitionId(), result.getRequisitionId());
            assertEquals(responseDTO.getRetiredDate(), result.getRetiredDate());
            assertEquals(responseDTO.getDevolutionDate(), result.getDevolutionDate());
            assertEquals(StatusIndicator.POSTERGADO, result.getStatusIndicator());

        });
    }

    @Test
    void shouldThrowExceptionWhenTryUpdateRequisitionWithoutRequisitionId() {
        Exception actualException = assertThrows(Exception.class, () -> {

            requestDTO.setRequisitionId(null);
            requestDTO.setAction(StatusIndicator.POSTERGADO.getAction());

            when(associateService.getByUsername(any(), any())).thenReturn(associate);

            requisitionService.makeRequisitionByAction(requestDTO, associate);
        });

        Exception expectedException = new RequisitionException("É necessário o Id da requisição.", HttpStatus.BAD_REQUEST);

        assertInstanceOf(RequisitionException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldMakeRequisitionToClose() {
        assertDoesNotThrow(() -> {

            requestDTO.setAction(StatusIndicator.FINALIZADO.getAction());

            when(requisitionRepository.findById(any())).thenReturn(Optional.of(requisition));
            when(requisitionRepository.save(any())).thenReturn(requisition);

            RequisitionResponseDTO result = requisitionService.makeRequisitionByAction(requestDTO, associate);

            assertEquals(responseDTO.getRequisitionId(), result.getRequisitionId());
            assertEquals(responseDTO.getRetiredDate(), result.getRetiredDate());
            assertNull(result.getDevolutionDate());
            assertEquals(StatusIndicator.FINALIZADO, result.getStatusIndicator());

        });
    }

    @Test
    void shouldThrowExceptionWhenCantFindByRequisitionId() {
        Exception actualException = assertThrows(Exception.class, () -> {

            requestDTO.setAction(StatusIndicator.FINALIZADO.getAction());

            when(associateService.getByUsername(any(), any())).thenReturn(associate);
            when(requisitionRepository.findById(any())).thenReturn(Optional.empty());

            requisitionService.makeRequisitionByAction(requestDTO, associate);
        });

        Exception expectedException = new RequisitionException("Requisition não existe!", HttpStatus.NOT_FOUND);

        assertInstanceOf(RequisitionException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void shouldGetRequisitionForAssociate() {
        assertDoesNotThrow(() -> {

            when(associateService.getById(anyLong(), any())).thenReturn(associate);

            List<RequisitionResponseDTO> result = requisitionService.getRequisitionForAssociate(1L, associate);

            assertTrue(result.stream().allMatch(r -> r.getRequisitionId() == responseDTO.getRequisitionId()));
            assertTrue(result.stream().allMatch(r -> r.getStatusIndicator() != StatusIndicator.FINALIZADO));

        });
    }

    @Test
    void shouldGetRequisitionCloseToExpire() {
        assertDoesNotThrow(() -> {

            when(associateRepository.findAll((Pageable) any())).thenReturn(associates);

            RequisitionPageDTO result = requisitionService.getRequisitionCloseToExpire(1, 10);

            assertNotNull(result.getData());

            assertTrue(result.getData().stream().allMatch(arDTO -> arDTO.getRequisitions()
                    .stream().noneMatch(req -> req.getStatus().equals(StatusIndicator.FINALIZADO))));

        });
    }

    @Test
    void shouldThrowExceptionWhenPageIsEmpty() {
        Exception actualException = assertThrows(Exception.class, () -> {

            when(associateRepository.findAll((Pageable) any())).thenReturn(Page.empty());

            requisitionService.getRequisitionCloseToExpire(1, 10);
        });

        Exception expectedException = new RequisitionException("Nenhuma requisição encontrada.", HttpStatus.NOT_FOUND);

        assertInstanceOf(RequisitionException.class, actualException);
        assertEquals(expectedException.getCause(), actualException.getCause());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }
}
