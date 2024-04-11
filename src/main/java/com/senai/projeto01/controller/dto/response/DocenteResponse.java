package com.senai.projeto01.controller.dto.response;

import java.time.LocalDate;

public record DocenteResponse(
        Long id,
        String nome,
        LocalDate dataEntrada,
        String nomeUsuario,
        String papel
) {
}
