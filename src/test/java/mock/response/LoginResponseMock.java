package mock.response;

import com.raphael.Library.dto.login.LoginResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LoginResponseMock {

    public static LoginResponse toResponse() {
        return new LoginResponse("test", LocalDateTime.now().plusHours(2L).toInstant(ZoneOffset.of("-03:00")));
    }
}
