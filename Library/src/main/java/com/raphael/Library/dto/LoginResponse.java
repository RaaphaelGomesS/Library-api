package com.raphael.Library.dto;

import java.time.Instant;

public record LoginResponse(String accessToken, Instant expiresIn) {
}
