package com.senai.projeto01.controller.dto.response;

public record UsuarioResponse(
        Long id,
        String nomeUsuario,
        String papel
) {
}
