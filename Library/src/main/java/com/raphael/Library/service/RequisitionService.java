package com.raphael.Library.service;

import com.raphael.Library.builder.RequisitionResponseDTOBuilder;
import com.raphael.Library.dto.RequisitionRequestDTO;
import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.RequisitionException;
import com.raphael.Library.indicator.StatusIndicator;
import com.raphael.Library.repository.RequisitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequisitionService {

    private final RequisitionRepository requisitionRepository;

    private final BookService bookService;

    private final AssociateService associateService;

    public RequisitionResponseDTO makeRequisitionByAction(RequisitionRequestDTO requestDTO) throws Exception {

        StatusIndicator statusIndicator = StatusIndicator.getValueByAction(requestDTO.getAction());

        if (statusIndicator == null) {
            throw new RequisitionException("Não é possivel criar uma requisição para essa operação!", HttpStatus.CONFLICT);
        }

        return switch (statusIndicator) {
            case ABERTO -> createRequisition(requestDTO);
            case POSTERGADO -> updateRequisition(requestDTO);
            case FINALIZADO -> closeRequisition(requestDTO);
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


    private RequisitionResponseDTO updateRequisition(RequisitionRequestDTO requestDTO) throws Exception {

        Optional<Requisition> optionalRequisition = requisitionRepository.findById(requestDTO.getRequisitionId());

        if (optionalRequisition.isEmpty()) {
            throw new RequisitionException("Requisition not exist!", HttpStatus.NOT_FOUND);
        }

        Requisition requisition = optionalRequisition.get();

        requisition.setStatusIndicator(StatusIndicator.POSTERGADO);
        requisition.setDevolutionDate(LocalDate.now().plusWeeks(1L));

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }

    private RequisitionResponseDTO closeRequisition(RequisitionRequestDTO requestDTO) throws Exception {

        Optional<Requisition> optionalRequisition = requisitionRepository.findById(requestDTO.getRequisitionId());

        if (optionalRequisition.isEmpty()) {
            throw new RequisitionException("Requisition not exist!", HttpStatus.NOT_FOUND);
        }

        Requisition requisition = optionalRequisition.get();

        requisition.setStatusIndicator(StatusIndicator.FINALIZADO);
        requisition.setDevolutionDate(null);

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }
}
