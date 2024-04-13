package com.senai.projeto01.controller.dto.response;

public record MateriaComCursoResponse(
        Long id,
        String nome,
        CursoResponse curso
) {
}
