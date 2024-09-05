package com.raphael.Library.builder;

import com.raphael.Library.dto.associate.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@UtilityClass

public class AssociateBuilder {

    private static PasswordEncoder passwordEncoder;

    public static Associate from(AssociateRequestDTO associateRequestDTO) {
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

    public static Associate fromAssociate(AssociateRequestDTO associateRequestDTO, Long associateId) {
        return Associate
                .builder()
                .associateId(associateId)
                .name(associateRequestDTO.getName())
                .email(associateRequestDTO.getEmail())
                .username(associateRequestDTO.getUsername())
                .password(passwordEncoder.encode(associateRequestDTO.getPassword()))
                .build();
    }
}
