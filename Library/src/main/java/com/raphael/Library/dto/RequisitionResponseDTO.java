package com.raphael.Library.dto;

import com.raphael.Library.indicator.StatusIndicator;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionResponseDTO {


    private long requisitionId;

    private StatusIndicator statusIndicator;

    private LocalDate retiredDate;

    private LocalDate updateDate;

    private LocalDate devolutionDate;
}
