package com.raphael.Library.builder;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Builder
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
