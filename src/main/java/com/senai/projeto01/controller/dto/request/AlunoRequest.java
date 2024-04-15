package com.senai.projeto01.controller.dto.request;

public record AlunoRequest(
        String nome,
        String dataNascimento,
        Long usuarioId,
        Long turmaId
) {
}
