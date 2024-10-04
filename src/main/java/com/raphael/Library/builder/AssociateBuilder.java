package com.raphael.Library.builder;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class AssociateBuilder {

    private final PasswordEncoder passwordEncoder;

    public Associate from(AssociateRequestDTO associateRequestDTO) {
        return Associate
                .builder()
                .name(associateRequestDTO.getName())
                .email(associateRequestDTO.getEmail())
                .username(associateRequestDTO.getUsername())
                .password(passwordEncoder.encode(associateRequestDTO.getPassword()))
                .role(Associate.RoleIndicator.DEFAULT)
                .booksInPossession(new ArrayList<>())
                .build();
    }

    public void fromAssociate(AssociateRequestDTO associateRequestDTO, Associate associate) {

        associate.setName(associateRequestDTO.getName());
        associate.setEmail(associateRequestDTO.getEmail());
        associate.setUsername(associateRequestDTO.getUsername());
        associate.setPassword(passwordEncoder.encode(associateRequestDTO.getPassword()));
    }
}
