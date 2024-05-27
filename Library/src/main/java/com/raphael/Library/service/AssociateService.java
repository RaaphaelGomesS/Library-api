package com.raphael.Library.service;

import com.raphael.Library.builder.AssociateBuilder;
import com.raphael.Library.dto.AssociateDTO;
import com.raphael.Library.entities.Associate;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AssociateService {

    private AssociateRepository associateRepository;

    public Associate createAssociate(AssociateDTO associateDTO) throws AssociateException {

        verifyAlreadyExist(associateDTO);

        Associate associate = AssociateBuilder.from(associateDTO);

        associateRepository.save(associate);

        return associate;
    }

    private void verifyAlreadyExist(AssociateDTO associateDTO) throws AssociateException {

        verifyEmail(associateDTO.getEmail());

        Optional<Associate> optionalAssociate = associateRepository.getByEmail(associateDTO.getEmail());

        if (optionalAssociate.isPresent()) {
            throw new AssociateException("O Associado já está registrado!");
        }
    }

    private void verifyEmail(String email) throws AssociateException {

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new AssociateException("O Email é inválido!");
        }
    }
}
