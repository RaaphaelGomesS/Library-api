package mock.response;

import com.raphael.Library.dto.requisition.RequisitionResponseDTO;
import com.raphael.Library.indicator.StatusIndicator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequisitionResponseDTOMock {

    public static RequisitionResponseDTO toResponseDTO() {
        return RequisitionResponseDTO
                .builder()
                .requisitionId(1L)
                .statusIndicator(StatusIndicator.ABERTO)
                .retiredDate(LocalDate.now())
                .devolutionDate(LocalDate.now().plusWeeks(1L))
                .build();
    }

    public static List<RequisitionResponseDTO> toList() {
        List<RequisitionResponseDTO> requisitions = new ArrayList<>();

        requisitions.add(toResponseDTO());

        return requisitions;
    }
}
