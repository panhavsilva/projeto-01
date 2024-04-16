package com.senai.projeto01.controller.dto.request;

public record UsuarioRequest(
        String nomeUsuario,
        String senha,
        String nomePapel
) {
}
