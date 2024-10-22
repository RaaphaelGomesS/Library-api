package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.builder.AssociateResponseDTOBuilder;
import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
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

    private final AssociateBuilder associateBuilder;

    public AssociateResponseDTO createAssociate(AssociateRequestDTO associateRequestDTO) throws AssociateException {

        Optional<Associate> optionalAssociate = associateRepository.findByUsername(associateRequestDTO.getUsername());

        if (optionalAssociate.isPresent()) {
            throw new AssociateException("O username já está em uso.", HttpStatus.CONFLICT);
        }

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        Associate associate = associateBuilder.from(associateRequestDTO);

        associateRepository.save(associate);

        return AssociateResponseDTOBuilder.from(associate);
    }


    public AssociateResponseDTO updateAssociate(AssociateRequestDTO associateRequestDTO, Associate associateAuthenticated) throws Exception {

        Associate associate = getById(associateRequestDTO.getId(), associateAuthenticated);

        ValidationUtils.verifyEmail(associateRequestDTO.getEmail());
        ValidationUtils.verifyPassword(associateRequestDTO.getPassword());

        associateBuilder.fromAssociate(associateRequestDTO, associate);

        associateRepository.save(associate);

        return AssociateResponseDTOBuilder.from(associate);
    }

    public void deleteAssociate(long associateId, Associate associate) throws Exception {

        associate = getById(associateId, associate);

        associateRepository.delete(associate);
    }

    public Associate getById(long associateId, Associate associate) throws Exception {

        if (!associate.getRole().equals(Associate.RoleIndicator.ADMIN)) {
            return associate;
        }

        return associateRepository.findById(associateId)
                .orElseThrow(() -> new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND));
    }

    public Associate getByUsername(String username, Associate associate) throws Exception {

        if (!associate.getRole().equals(Associate.RoleIndicator.ADMIN)) {
            return associate;
        }

        return associateRepository.findByUsername(username)
                .orElseThrow(() -> new AssociateException("Associado não encontrado.", HttpStatus.NOT_FOUND));
    }
}
