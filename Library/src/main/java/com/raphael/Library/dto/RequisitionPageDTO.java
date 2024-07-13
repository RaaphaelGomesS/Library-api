package com.raphael.Library.dto;

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
