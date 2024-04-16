package com.senai.projeto01.controller.dto.response;

import java.time.LocalDate;

public record NotaSemAlunoResponse(
        Long id,
        Double nota,
        LocalDate data,
        MateriaResponse materia
) {
}
