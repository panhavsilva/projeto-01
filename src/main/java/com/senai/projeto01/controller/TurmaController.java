package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.TurmaRequest;
import com.senai.projeto01.controller.dto.response.TurmaResponse;
import com.senai.projeto01.service.TurmaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/turmas")
public class TurmaController {
    private final TurmaService service;

    @GetMapping
    public ResponseEntity<List<TurmaResponse>> get(){
        log.info("GET /turmas - solicitação recebida para buscar todos as turmas.");
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<TurmaResponse> getPorId(@PathVariable Long id){
        log.info("GET /turmas/:id - solicitação recebida para buscar turma com id {}.", id);
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TurmaResponse> post(@RequestBody TurmaRequest turmaRequest) throws Exception {
        log.info("POST /turmas - solicitação recebida para cadastrar nova turma");
        return ResponseEntity.ok().body(service.criarNovoTurma(turmaRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<TurmaResponse> put(
            @PathVariable Long id,
            @RequestBody TurmaRequest turmaRequest
    ) throws Exception {
        log.info("PUT /turmas/:id - solicitação recebida para atualizar cadastro da turma com id {}.", id);
        return ResponseEntity.ok().body(service.atualizar(id, turmaRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info("DELETE /turmas/:id - solicitação recebida para excluir turma com id {}.", id);
        service.excluir(id);
        return ResponseEntity.ok().body("Turma excluída com sucesso");
    }
}
