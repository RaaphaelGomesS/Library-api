package com.raphael.Library.dto.associate;

import com.raphael.Library.entities.Requisition;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociateRequisitionDTO {

    private long associateId;

    private List<Requisition> requisitions;
}
