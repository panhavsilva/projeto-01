package com.senai.projeto01.controller.dto.request;

public record TurmaRequest(
        String nome,
        Long professorId,
        Long cursoId
) {
}
