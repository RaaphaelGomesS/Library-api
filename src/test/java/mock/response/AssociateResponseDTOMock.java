package mock.response;

import com.raphael.Library.dto.associate.AssociateResponseDTO;

public class AssociateResponseDTOMock {

    public static AssociateResponseDTO toResponseDTO() {

        return AssociateResponseDTO
                .builder()
                .id(1L)
                .name("Raphael")
                .email("emailteste@gmail.com")
                .username("test")
                .booksInPossession(RequisitionResponseDTOMock.toList())
                .build();
    }
}
