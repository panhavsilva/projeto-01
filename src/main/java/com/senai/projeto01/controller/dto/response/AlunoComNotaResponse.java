package com.senai.projeto01.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

public record AlunoComNotaResponse(
        Long id,
        String nome,
        LocalDate dataNascimento,
        String nomeUsuario,
        TurmaResponse turma,
        List<NotaSemAlunoResponse> notas
) {
}
