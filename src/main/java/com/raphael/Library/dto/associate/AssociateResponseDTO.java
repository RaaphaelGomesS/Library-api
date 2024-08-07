package com.raphael.Library.dto.associate;

import com.raphael.Library.entities.Requisition;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateResponseDTO {

    private String name;

    private String email;

    private String username;

    private List<Requisition> booksInPossession;
}
