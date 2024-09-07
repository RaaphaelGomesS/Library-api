package mock.request;

import com.raphael.Library.dto.associate.AssociateRequestDTO;

public class AssociateRequestDTOMock {

    public static AssociateRequestDTO toRequestDTO() {
        return AssociateRequestDTO
                .builder()
                .id(1L)
                .name("Raphael")
                .email("emailteste@gmail.com")
                .username("test")
                .password("Senha123@")
                .build();
    }
}
