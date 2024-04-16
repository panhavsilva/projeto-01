package com.senai.projeto01.controller.dto.request;

public record NotaRequest(
        Double nota,
        Long alunoId,
        Long materiaId
) {
}
