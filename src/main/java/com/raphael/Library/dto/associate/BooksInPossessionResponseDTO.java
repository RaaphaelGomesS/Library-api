package com.raphael.Library.dto.associate;

import com.raphael.Library.indicator.StatusIndicator;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooksInPossessionResponseDTO {

    private long requisitionId;

    private String bookName;

    private StatusIndicator status;

    private LocalDate retiredDate;

    private LocalDate updateDate;

    private LocalDate devolutionDate;
}
