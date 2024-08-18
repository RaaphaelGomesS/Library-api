package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateRequisitionDTOBuilder;
import com.raphael.Library.builder.RequisitionResponseDTOBuilder;
import com.raphael.Library.dto.associate.AssociateRequisitionDTO;
import com.raphael.Library.dto.requisition.RequisitionPageDTO;
import com.raphael.Library.dto.requisition.RequisitionRequestDTO;
import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
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

    public RequisitionResponseDTO makeRequisitionByAction(RequisitionRequestDTO requestDTO, Associate associateByToken) throws Exception {

        Associate associate = associateService.getByUsername(requestDTO.getAssociate(), associateByToken);

        StatusIndicator statusIndicator = StatusIndicator.getValueByAction(requestDTO.getAction());

        if (statusIndicator == null) {
            throw new RequisitionException("Não é possivel realizar essa ação, tente: abrir, renovar ou fechar.", HttpStatus.CONFLICT);
        }

        return switch (statusIndicator) {
            case ABERTO -> createRequisition(requestDTO, associate);
            case POSTERGADO -> updateRequisition(requestDTO);
            case FINALIZADO -> closeRequisition(requestDTO);
        };
    }

    public List<RequisitionResponseDTO> getRequisitionForAssociate(long associateId, Associate associateByToken) throws Exception {

        Associate associate = associateService.getById(associateId, associateByToken);

        return associate.getBooksInPossession().stream()
                .filter(requisition -> requisition.getStatusIndicator() != StatusIndicator.FINALIZADO)
                .map(RequisitionResponseDTOBuilder::from)
                .toList();
    }

    public RequisitionPageDTO getRequisitionCloseToExpire(int page, int pageSize) {

        Page<Associate> associates = associateRepository.findAll(PageRequest.of(page, pageSize));

        List<AssociateRequisitionDTO> requisitionDTOS = associates.getContent().stream().map(AssociateRequisitionDTOBuilder::from).toList();

        return new RequisitionPageDTO(page, pageSize, associates.getTotalPages(), associates.getTotalElements(), requisitionDTOS);
    }

    private RequisitionResponseDTO createRequisition(RequisitionRequestDTO requestDTO, Associate associate) throws Exception {

        Book book = bookRepository.findByName(requestDTO.getBookName())
                .orElseThrow(() -> new BookException("Livro não encontrado.", HttpStatus.NOT_FOUND));

        Requisition requisition = Requisition
                .builder()
                .book(book)
                .associate(associate)
                .statusIndicator(StatusIndicator.ABERTO)
                .retiredDate(LocalDate.now())
                .devolutionDate(LocalDate.now().plusWeeks(1L))
                .build();

        associateService.addRequisition(requisition);

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }


    private RequisitionResponseDTO updateRequisition(RequisitionRequestDTO requestDTO) throws RequisitionException {

        if (requestDTO.getRequisitionId() == null) {
            throw new RequisitionException("É necessário o Id da requisição para atualiza-la.", HttpStatus.BAD_REQUEST);
        }

        Requisition requisition = requisitionRepository.findById(Long.parseLong(requestDTO.getRequisitionId()))
                .orElseThrow(() -> new RequisitionException("Requisition não existe!", HttpStatus.NOT_FOUND));

        requisition.setStatusIndicator(StatusIndicator.POSTERGADO);
        requisition.setDevolutionDate(LocalDate.now().plusWeeks(1L));

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }

    private RequisitionResponseDTO closeRequisition(RequisitionRequestDTO requestDTO) throws RequisitionException {

        if (requestDTO.getRequisitionId() == null) {
            throw new RequisitionException("É necessário o Id da requisição para finaliza-la.", HttpStatus.BAD_REQUEST);
        }

        Requisition requisition = requisitionRepository.findById(Long.parseLong(requestDTO.getRequisitionId()))
                .orElseThrow(() -> new RequisitionException("Requisition não existe!", HttpStatus.NOT_FOUND));

        requisition.setStatusIndicator(StatusIndicator.FINALIZADO);
        requisition.setDevolutionDate(null);

        requisitionRepository.save(requisition);

        return RequisitionResponseDTOBuilder.from(requisition);
    }
}
