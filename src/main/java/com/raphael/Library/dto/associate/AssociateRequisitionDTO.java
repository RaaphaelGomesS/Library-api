package com.raphael.Library.dto.associate;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateRequisitionDTO {

    private long associateId;

    private List<BooksInPossessionResponseDTO> requisitions;
}
