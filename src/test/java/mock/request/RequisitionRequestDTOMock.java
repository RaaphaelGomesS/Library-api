package mock.request;

import com.raphael.Library.dto.requisition.RequisitionRequestDTO;

public class RequisitionRequestDTOMock {

    public static RequisitionRequestDTO toRequest() {
        return RequisitionRequestDTO
                .builder()
                .requisitionId("1")
                .bookName("CRIME E CASTIGO")
                .associate("1")
                .action("criar")
                .build();
    }
}
