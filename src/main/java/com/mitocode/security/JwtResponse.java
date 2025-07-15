package com.mitocode.security;

import com.fasterxml.jackson.annotation.JsonProperty;

// Clase S4
public record JwtResponse(@JsonProperty("access_token") String accessToken) {
}
