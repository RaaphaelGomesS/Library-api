package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.builder.AssociateResponseDTOBuilder;
import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.dto.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.entities.Requisition;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
            throw new AssociateException("The username is already registered!", HttpStatus.CONFLICT);
        }

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        Associate associate = AssociateBuilder.from(associateRequestDTO, passwordEncoder);

        associateRepository.save(associate);

        return AssociateResponseDTOBuilder.from(associate);
    }


    public AssociateResponseDTO updateAssociate(AssociateRequestDTO associateRequestDTO, JwtAuthenticationToken token) throws Exception {

        Associate associate = associateRepository.findByUsername(associateRequestDTO.getUsername()).orElseThrow(() -> new AssociateException("Not found any associate with this username!", HttpStatus.NOT_FOUND));

        ValidationUtils.verifyHasPermission(token, associate);
        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        associate.setName(associateRequestDTO.getName());
        associate.setEmail(associateRequestDTO.getEmail());
        associate.setUsername(associateRequestDTO.getUsername());
        associate.setPassword(passwordEncoder.encode(associateRequestDTO.getPassword()));

        associateRepository.save(associate);

        return AssociateResponseDTOBuilder.from(associate);
    }

    public void deleteAssociate(long associateId, JwtAuthenticationToken token) throws Exception {

        Associate associate = getById(associateId, token);

        associateRepository.delete(associate);
    }

    public Associate getById(long associateId, JwtAuthenticationToken token) throws Exception {

        Associate associate = associateRepository.findById(associateId).orElseThrow(() -> new AssociateException("Not found any associate with this id!", HttpStatus.NOT_FOUND));

        ValidationUtils.verifyHasPermission(token, associate);

        return associate;
    }

    public void addRequisition(Requisition requisition) throws Exception {

        ValidationUtils.verifyManyRequisitionHave(requisition);

        Associate associate = requisition.getAssociate();

        associate.getBooksInPossession().add(requisition);

        associateRepository.save(associate);
    }
}
