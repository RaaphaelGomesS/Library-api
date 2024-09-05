package mock.entities;

import com.raphael.Library.entities.Requisition;
import com.raphael.Library.indicator.StatusIndicator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequisitionMock {

    public static Requisition toEntity() {
        return Requisition.builder()
                .requisitionId(1L)
                .associateId(1L)
                .bookId(1L)
                .statusIndicator(StatusIndicator.ABERTO)
                .retiredDate(LocalDate.now())
                .updateDate(null)
                .devolutionDate(LocalDate.now().plusWeeks(1L))
                .build();
    }

    public static List<Requisition> toList() {
        List<Requisition> requisitions = new ArrayList<>();

        requisitions.add(toEntity());

        return requisitions;
    }
}
