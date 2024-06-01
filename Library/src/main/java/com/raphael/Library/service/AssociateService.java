package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.dto.AssociateDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import com.raphael.Library.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AssociateService {

    private AssociateRepository associateRepository;

    public Associate createAssociate(AssociateDTO associateDTO) throws AssociateException {

        verifyAlreadyExist(associateDTO);

        ValidationUtils.verifyNumber(associateDTO.getPhone());

        Associate associate = AssociateBuilder.from(associateDTO);

        associateRepository.save(associate);

        return associate;
    }


    public Associate updateAssociate(UUID associateId, AssociateDTO associateDTO) throws AssociateException {

        Associate associate = getById(associateId);

        ValidationUtils.verifyEmail(associateDTO.getEmail());
        ValidationUtils.verifyNumber(associateDTO.getPhone());

        associate.setName(associateDTO.getName());
        associate.setEmail(associateDTO.getEmail());
        associate.setPhone(associateDTO.getPhone());

        associateRepository.save(associate);

        return associate;
    }

    public void deleteAssociate(UUID associateId) throws AssociateException {

        Associate associate = getById(associateId);

        associateRepository.delete(associate);

    }

    private void verifyAlreadyExist(AssociateDTO associateDTO) throws AssociateException {

        ValidationUtils.verifyEmail(associateDTO.getEmail());

        Optional<Associate> optionalAssociate = associateRepository.findByEmail(associateDTO.getEmail());

        if (optionalAssociate.isPresent()) {
            throw new AssociateException("O Associado já está registrado!");
        }
    }

    public Associate getById(UUID associateId) throws AssociateException {

        Optional<Associate> associate = associateRepository.findById(associateId);

        if (associate.isEmpty()) {
            throw new AssociateException("Não existe nenhum associado com esse id!");
        }

        return associate.get();
    }
}
