package com.senai.projeto01.exception;

import com.senai.projeto01.exception.dto.Erro;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception e) {
        Erro erro = Erro.builder()
                .codigo("500")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(500).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handler(DataIntegrityViolationException e) {
        Erro erro = Erro.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handler(BadRequestException e) {
        Erro erro = Erro.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handler(NotFoundException e) {
        Erro erro = Erro.builder()
                .codigo("404")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handler(AccessDeniedException e) {
        Erro erro = Erro.builder()
                .codigo("403")
                .mensagem("Acesso negado. " + e.getMessage())
                .build();
        return ResponseEntity.status(403).body(erro);
    }
}
