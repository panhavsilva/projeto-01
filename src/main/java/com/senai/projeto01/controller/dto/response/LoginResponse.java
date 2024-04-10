package com.senai.projeto01.controller.dto.response;

public record LoginResponse(
        String valorJWT,
        long tempoExpiracao
) {
}
