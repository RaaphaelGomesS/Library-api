package com.raphael.Library.service;

import com.raphael.Library.builder.RequisitionResponseDTOBuilder;
import com.raphael.Library.dto.RequisitionRequestDTO;
import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.exception.RequisitionException;
import com.raphael.Library.indicator.StatusIndicator;
import com.raphael.Library.repository.RequisitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequisitionService {

    private RequisitionRepository requisitionRepository;

    private BookService bookService;

    private AssociateService associateService;

    public RequisitionResponseDTO makeRequisitionByAction(RequisitionRequestDTO requestDTO) throws Exception {

        StatusIndicator statusIndicator = StatusIndicator.getValueByAction(requestDTO.getAction());

        if (statusIndicator == null) {
            throw new RequisitionException("Não é possivel criar uma requisição para essa operação!");
        }

        return switch (statusIndicator) {
            case ABERTO -> createRequisition(requestDTO);
            case POSTERGADO -> null;
            case FINALIZADO -> null;
        };
    }

    private RequisitionResponseDTO createRequisition(RequisitionRequestDTO requestDTO) throws Exception {

        Book book = bookService.getBookById(requestDTO.getBookId());
        Associate associate = associateService.getById(requestDTO.getAssociateId());

        Requisition requisition = Requisition
                .builder()
                .book(book)
                .associate(associate)
                .statusIndicator(StatusIndicator.ABERTO)
                .build();

        requisition.setDevolutionDate(LocalDate.now().plusWeeks(1L));

        requisitionRepository.save(requisition);

        associateService.addRequisition(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }

    //to do
//    private RequisitionResponseDTO updateRequisition(RequisitionRequestDTO requestDTO) throws Exception {
//
//        Book book = bookService.getBookById(requestDTO.getBookId());
//        Associate associate = associateService.getById(requestDTO.getAssociateId());
//
//
//
//    }
//
//    private RequisitionResponseDTO closeRequisition(RequisitionRequestDTO requestDTO) throws Exception {
//
//        Book book = bookService.getBookById(requestDTO.getBookId());
//        Associate associate = associateService.getById(requestDTO.getAssociateId());
//    }
}
