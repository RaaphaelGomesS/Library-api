package com.raphael.Library.dto.requisition;

import com.raphael.Library.dto.associate.AssociateRequisitionDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionPageDTO {

    private int page;

    private int pageSize;

    private int totalPage;

    private long totalElements;

    private List<AssociateRequisitionDTO> data;
}
