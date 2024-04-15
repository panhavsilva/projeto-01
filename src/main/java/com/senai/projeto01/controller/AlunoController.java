package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.AlunoRequest;
import com.senai.projeto01.controller.dto.response.AlunoResponse;
import com.senai.projeto01.service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alunos")
public class AlunoController {
    private final AlunoService service;

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> get(){
        log.info("GET /alunos - solicitação recebida para buscar todos as alunos.");
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<AlunoResponse> getPorId(@PathVariable Long id){
        log.info("GET /alunos/:id - solicitação recebida para buscar aluno com id {}.", id);
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> post(@RequestBody AlunoRequest alunoRequest) throws Exception {
        log.info("POST /alunos - solicitação recebida para cadastrar novo aluno");
        return ResponseEntity.ok().body(service.criarNovoAluno(alunoRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoResponse> put(
            @PathVariable Long id,
            @RequestBody AlunoRequest alunoRequest
    ) throws Exception {
        log.info("PUT /alunos/:id - solicitação recebida para atualizar cadastro do aluno com id {}.", id);
        return ResponseEntity.ok().body(service.atualizar(id, alunoRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info("DELETE /alunos/:id - solicitação recebida para excluir aluno com id {}.", id);
        service.excluir(id);
        return ResponseEntity.ok().body("Aluno excluído com sucesso");
    }
}
