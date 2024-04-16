package com.senai.projeto01.controller.dto.request;

public record LoginRequest(
        String nomeUsuario,
        String senha
) {
}
