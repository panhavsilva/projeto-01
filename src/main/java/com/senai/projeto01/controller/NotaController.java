package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.NotaRequest;
import com.senai.projeto01.controller.dto.response.NotaResponse;
import com.senai.projeto01.service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notas")
public class NotaController {
    private final NotaService service;

    @GetMapping
    public ResponseEntity<List<NotaResponse>> get(){
        log.info("GET /notas - solicitação recebida para buscar todos as notas.");
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<NotaResponse> getPorId(@PathVariable Long id){
        log.info("GET /notas/:id - solicitação recebida para buscar nota com id {}.", id);
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<NotaResponse> post(@RequestBody NotaRequest notaRequest) throws Exception {
        log.info("POST /notas - solicitação recebida para cadastrar nova nota");
        return ResponseEntity.ok().body(service.criarNovoNota(notaRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<NotaResponse> put(
            @PathVariable Long id,
            @RequestBody NotaRequest notaRequest
    ) throws Exception {
        log.info("PUT /notas/:id - solicitação recebida para atualizar cadastro da nota com id {}.", id);
        return ResponseEntity.ok().body(service.atualizar(id, notaRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info("DELETE /notas/:id - solicitação recebida para excluir nota com id {}.", id);
        service.excluir(id);
        return ResponseEntity.ok().body("Nota excluída com sucesso");
    }
}
