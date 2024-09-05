package mock.entities;

import com.raphael.Library.entities.Associate;

import java.time.Instant;

public class AssociateMock {

    public static Associate toEntity() {
        return Associate.builder()
                .associateId(1L)
                .name("Raphael")
                .email("emailteste@gmail.com")
                .username("teste")
                .password("teste")
                .role(Associate.RoleIndicator.DEFAULT)
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .booksInPossession(RequisitionMock.toList())
                .build();
    }
}
