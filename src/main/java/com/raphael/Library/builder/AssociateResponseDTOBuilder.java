package com.raphael.Library.builder;

import com.raphael.Library.dto.associate.AssociateResponseDTO;
import com.raphael.Library.entities.Associate;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class AssociateResponseDTOBuilder {
    public static AssociateResponseDTO from(Associate associate) {
        return AssociateResponseDTO
                .builder()
                .name(associate.getName())
                .email(associate.getEmail())
                .username(associate.getUsername())
                .booksInPossession(new ArrayList<>())
                .build();
    }

}
