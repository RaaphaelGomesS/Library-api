package com.raphael.Library.builder;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@UtilityClass
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
