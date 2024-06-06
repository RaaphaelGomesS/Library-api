package com.raphael.Library.dto;

import com.raphael.Library.indicator.StatusIndicator;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionResponseDTO {


    private UUID requisitionId;

    private StatusIndicator statusIndicator;

    private LocalDate retiredDate;

    private LocalDate updateDate;

    private LocalDate devolutionDate;
}
