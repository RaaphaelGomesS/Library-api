package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateRequisitionDTOBuilder;
import com.raphael.Library.builder.RequisitionResponseDTOBuilder;
import com.raphael.Library.dto.AssociateRequisitionDTO;
import com.raphael.Library.dto.RequisitionPageDTO;
import com.raphael.Library.dto.RequisitionRequestDTO;
import com.raphael.Library.dto.RequisitionResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.entities.books.Book;
import com.raphael.Library.exception.BookException;
import com.raphael.Library.exception.RequisitionException;
import com.raphael.Library.indicator.StatusIndicator;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.repository.BookRepository;
import com.raphael.Library.repository.RequisitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RequisitionService {

    private final RequisitionRepository requisitionRepository;

    private final BookRepository bookRepository;

    private final AssociateRepository associateRepository;

    private final AssociateService associateService;

    public RequisitionResponseDTO makeRequisitionByAction(RequisitionRequestDTO requestDTO) throws Exception {

        Associate associate = associateService.getById(requestDTO.getAssociateId());

        StatusIndicator statusIndicator = StatusIndicator.getValueByAction(requestDTO.getAction());

        if (statusIndicator == null) {
            throw new RequisitionException("Not is possible create a requisition for this action.", HttpStatus.CONFLICT);
        }

        Requisition requisition = requisitionRepository.findById(requestDTO.getRequisitionId())
                .orElseThrow(() -> new RequisitionException("Requisition not exist!", HttpStatus.NOT_FOUND));

        return switch (statusIndicator) {
            case ABERTO -> createRequisition(requestDTO, associate);
            case POSTERGADO -> updateRequisition(requisition);
            case FINALIZADO -> closeRequisition(requisition);
        };
    }

    public List<RequisitionResponseDTO> getRequisitionForAssociate(long associateId) throws Exception {

        Associate associate = associateService.getById(associateId);

        return associate.getBooksInPossession().stream()
                .filter(requisition -> requisition.getStatusIndicator() != StatusIndicator.FINALIZADO)
                .map(RequisitionResponseDTOBuilder::from)
                .toList();
    }

    public RequisitionPageDTO getRequisitionCloseToExpire(int page, int pageSize) {

        Page<Associate> associates = associateRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "updateDate"));

        List<AssociateRequisitionDTO> requisitionDTOS = associates.stream().map(AssociateRequisitionDTOBuilder::from).toList();

        return new RequisitionPageDTO(page, pageSize, associates.getTotalPages(), associates.getTotalElements(), requisitionDTOS);
    }

    private RequisitionResponseDTO createRequisition(RequisitionRequestDTO requestDTO, Associate associate) throws Exception {

        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new BookException("Book not found!", HttpStatus.NOT_FOUND));

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
