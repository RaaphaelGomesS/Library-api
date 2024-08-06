package com.raphael.Library.dto.login;

import java.time.Instant;

public record LoginResponse(String accessToken, Instant expiresIn) {
}
