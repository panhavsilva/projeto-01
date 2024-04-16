package com.senai.projeto01.controller.dto.response;

import java.time.LocalDate;

public record AlunoResponse(
        Long id,
        String nome,
        LocalDate dataNascimento,
        String nomeUsuario,
        TurmaResponse turma
) {
}
