package com.senai.projeto01.controller.dto.response;

public record TurmaResponse(
        Long id,
        String nome,
        ProfessorResponse professor,
        CursoResponse curso
) {
}
