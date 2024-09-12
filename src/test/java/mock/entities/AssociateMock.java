package mock.entities;

import com.raphael.Library.entities.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class AssociateMock {

    public static Associate toEntity() {
        return Associate.builder()
                .associateId(1L)
                .name("Raphael")
                .email("emailteste@gmail.com")
                .username("teste")
                .password("Senha123@")
                .role(Associate.RoleIndicator.DEFAULT)
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .booksInPossession(RequisitionMock.toList())
                .build();
    }

    public static List<Associate> toList() {
        return Collections.singletonList(toEntity());
    }

    public static Page<Associate> toPage() {
        return new PageImpl<>(toList(), PageRequest.of(1, 10), toList().size());
    }
}
