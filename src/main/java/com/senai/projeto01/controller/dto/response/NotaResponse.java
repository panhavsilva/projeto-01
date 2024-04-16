package com.senai.projeto01.controller.dto.response;

import java.time.LocalDate;

public record NotaResponse(
        Long id,
        Double nota,
        LocalDate data,
        AlunoResponse aluno,
        MateriaResponse materia
) {
}
