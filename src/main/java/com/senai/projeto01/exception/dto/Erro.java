package com.senai.projeto01.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Erro {
    private String codigo;
    private String mensagem;
}
