package com.senai.projeto01.controller.dto.request;

public record DocenteRequest(
        String nome,
        String dataEntrada,
        Long usuarioId
) {
}
