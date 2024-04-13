package com.senai.projeto01.controller.dto.response;

import java.util.List;

public record CursoMateriasResponse(
        Long id,
        String nome,
        List<MateriaResponse> materias
) {
}
