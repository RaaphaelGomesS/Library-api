package com.raphael.Library.builder;

import com.raphael.Library.dto.AssociateRequestDTO;
import com.raphael.Library.entities.Associate;

import java.util.ArrayList;

public class AssociateBuilder {

    public static Associate from(AssociateRequestDTO associateRequestDTO) {
        return Associate
                .builder()
                .name(associateRequestDTO.getName())
                .email(associateRequestDTO.getEmail())
                .user(associateRequestDTO.getUser())
                .role(Associate.RoleIndicator.DEFAULT)
                .booksInPossession(new ArrayList<>())
                .build();
    }
}
