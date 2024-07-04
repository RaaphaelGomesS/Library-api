package com.raphael.Library.builder;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class AssociateBuilder {

    public static Associate from(AssociateRequestDTO associateRequestDTO, PasswordEncoder passwordEncoder) {
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
}
