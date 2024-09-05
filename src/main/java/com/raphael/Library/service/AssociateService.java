package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.builder.AssociateResponseDTOBuilder;
import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssociateService {

    private final AssociateRepository associateRepository;

    private final PasswordEncoder passwordEncoder;

    public AssociateResponseDTO createAssociate(AssociateRequestDTO associateRequestDTO) throws AssociateException {

        Optional<Associate> optionalAssociate = associateRepository.findByUsername(associateRequestDTO.getUsername());

        if (optionalAssociate.isPresent()) {
            throw new AssociateException("O username já está em uso.", HttpStatus.CONFLICT);
        }

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        Associate associate = AssociateBuilder.from(associateRequestDTO);

        associateRepository.save(associate);

        return AssociateResponseDTOBuilder.from(associate);
    }


    public AssociateResponseDTO updateAssociate(AssociateRequestDTO associateRequestDTO, Associate associateByToken) throws Exception {

        Associate associate = getById(associateRequestDTO.getId(), associateByToken);

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        AssociateBuilder.fromAssociate(associateRequestDTO, associate.getAssociateId());

        associateRepository.save(associate);

        return AssociateResponseDTOBuilder.from(associate);
    }

    public void deleteAssociate(long associateId, Associate associateByToken) throws Exception {

        Associate associate = getById(associateId, associateByToken);

        associateRepository.delete(associate);
    }

    public Associate getById(long associateId, Associate associateByToken) throws Exception {

        Associate associate = associateRepository.findById(associateId)
                .orElseThrow(() -> new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND));

        ValidationUtils.verifyHasPermission(associateByToken, associate);

        return associate;
    }

    public Associate getByUsername(String username, Associate associateByToken) throws Exception {

        Associate associate = associateRepository.findByUsername(username)
                .orElseThrow(() -> new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND));

        ValidationUtils.verifyHasPermission(associateByToken, associate);

        return associate;
    }

    public void addRequisition(Requisition requisition) throws Exception {

        ValidationUtils.verifyManyRequisitionHave(requisition);

        Associate associate = requisition.getAssociate();

        associate.getBooksInPossession().add(requisition);

        associateRepository.save(associate);
    }
}
