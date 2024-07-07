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
import com.raphael.Library.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequisitionService {

    private final RequisitionRepository requisitionRepository;

    private final BookService bookService;

    private final AssociateService associateService;

    public RequisitionResponseDTO makeRequisitionByAction(RequisitionRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        Associate associate = associateService.getById(requestDTO.getAssociateId());

        ValidationUtils.verifyHasPermission(token, associate);

        StatusIndicator statusIndicator = StatusIndicator.getValueByAction(requestDTO.getAction());

        if (statusIndicator == null) {
            throw new RequisitionException("Não é possivel criar uma requisição para essa operação!", HttpStatus.CONFLICT);
        }

        Requisition requisition = requisitionRepository.findById(requestDTO.getRequisitionId())
                .orElseThrow(() -> new RequisitionException("Requisition not exist!", HttpStatus.NOT_FOUND));

        return switch (statusIndicator) {
            case ABERTO -> createRequisition(requestDTO, associate);
            case POSTERGADO -> updateRequisition(requisition);
            case FINALIZADO -> closeRequisition(requisition);
        };
    }

    private RequisitionResponseDTO createRequisition(RequisitionRequestDTO requestDTO, Associate associate) throws Exception {

        Book book = bookService.getBookById(requestDTO.getBookId());

        Requisition requisition = Requisition
                .builder()
                .book(book)
                .associate(associate)
                .statusIndicator(StatusIndicator.ABERTO)
                .devolutionDate(LocalDate.now().plusWeeks(1L))
                .build();

        requisitionRepository.save(requisition);

        associateService.addRequisition(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }


    private RequisitionResponseDTO updateRequisition(Requisition requisition) {

        requisition.setStatusIndicator(StatusIndicator.POSTERGADO);
        requisition.setDevolutionDate(LocalDate.now().plusWeeks(1L));

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }

    private RequisitionResponseDTO closeRequisition(Requisition requisition) {

        requisition.setStatusIndicator(StatusIndicator.FINALIZADO);
        requisition.setDevolutionDate(null);

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }
}
