package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.dto.AssociateDTO;
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

    public Associate createAssociate(AssociateDTO associateDTO) throws AssociateException {

        verifyAlreadyExist(associateDTO);

        ValidationUtils.verifyNumber(associateDTO.getPhone());

        Associate associate = AssociateBuilder.from(associateDTO);

        associateRepository.save(associate);

        return associate;
    }


    public Associate updateAssociate(long associateId, AssociateDTO associateDTO) throws AssociateException {

        Associate associate = getById(associateId);

        ValidationUtils.verifyEmail(associateDTO.getEmail());
        ValidationUtils.verifyNumber(associateDTO.getPhone());

        associate.setName(associateDTO.getName());
        associate.setEmail(associateDTO.getEmail());
        associate.setPhone(associateDTO.getPhone());

        associateRepository.save(associate);

        return associate;
    }

    public void deleteAssociate(long associateId) throws AssociateException {

        Associate associate = getById(associateId);

        associateRepository.delete(associate);

    }

    private void verifyAlreadyExist(AssociateDTO associateDTO) throws AssociateException {

        ValidationUtils.verifyEmail(associateDTO.getEmail());

        Optional<Associate> optionalAssociate = associateRepository.findByEmail(associateDTO.getEmail());

        if (optionalAssociate.isPresent()) {
            throw new AssociateException("O Associado já está registrado!", HttpStatus.CONFLICT);
        }
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
