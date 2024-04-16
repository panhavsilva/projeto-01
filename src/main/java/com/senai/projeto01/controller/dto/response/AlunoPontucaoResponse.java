package com.senai.projeto01.controller.dto.response;

import java.time.LocalDate;

public record AlunoPontucaoResponse(
        Long id,
        String nome,
        LocalDate dataNascimento,
        String nomeUsuario,
        Double pontucao
) {
}
