package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssociateService {

    private final AssociateRepository associateRepository;

    public Associate createAssociate(AssociateRequestDTO associateRequestDTO) throws AssociateException {

        Optional<Associate> optionalAssociate = associateRepository.findByUsername(associateRequestDTO.getUsername());

        if (optionalAssociate.isPresent()) {
            throw new AssociateException("O username já está registrado!", HttpStatus.CONFLICT);
        }

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        Associate associate = AssociateBuilder.from(associateRequestDTO);

        associateRepository.save(associate);

        return associate;
    }


    public Associate updateAssociate(AssociateRequestDTO associateRequestDTO) throws AssociateException {

        Associate associate = associateRepository.findByUsername(associateRequestDTO.getUsername()).orElse(null);

        if (associate == null) {
            throw new AssociateException("O username nâo foi encotrado!", HttpStatus.NOT_FOUND);
        }

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        associate.setName(associateRequestDTO.getName());
        associate.setEmail(associateRequestDTO.getEmail());
        associate.setUsername(associateRequestDTO.getUsername());
        associate.setPassword(associateRequestDTO.getPassword());

        associateRepository.save(associate);

        return associate;
    }

    public void deleteAssociate(long associateId) throws AssociateException {

        Associate associate = getById(associateId);

        associateRepository.delete(associate);

    }

    public Associate getById(long associateId) throws AssociateException {

        Optional<Associate> associate = associateRepository.findById(associateId);

        if (associate.isEmpty()) {
            throw new AssociateException("Não existe nenhum associado com esse id!", HttpStatus.NOT_FOUND);
        }

        return associate.get();
    }

    public void addRequisition(Requisition requisition) throws Exception {

        ValidationUtils.verifyManyRequisitionHave(requisition);

        Associate associate = requisition.getAssociate();

        associate.getBooksInPossession().add(requisition);

        associateRepository.save(associate);
    }
}
