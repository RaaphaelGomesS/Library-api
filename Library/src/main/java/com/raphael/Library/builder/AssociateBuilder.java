package com.raphael.Library.builder;

import com.raphael.Library.dto.AssociateDTO;
import com.raphael.Library.entities.Associate;

public class AssociateBuilder {

    public static Associate from(AssociateDTO associateDTO) {
        return Associate
                .builder()
                .name(associateDTO.getName())
                .email(associateDTO.getEmail())
                .phone(associateDTO.getPhone())
                .build();
    }
}
