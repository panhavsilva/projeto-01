package com.senai.projeto01.controller.dto.response;

import com.senai.projeto01.datasource.entity.PapelEntity;

public record UsuarioResponse(
        Long id,
        String nomeUsuario,
        String papel
) {
}
