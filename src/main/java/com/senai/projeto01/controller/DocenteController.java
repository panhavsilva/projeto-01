package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.DocenteRequest;
import com.senai.projeto01.controller.dto.response.DocenteResponse;
import com.senai.projeto01.service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/docentes")
public class DocenteController {
    private final DocenteService service;

    @GetMapping
    public ResponseEntity<List<DocenteResponse>> get(){
        log.info("GET /docentes - solicitação recebida para buscar todos os docentes.");
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<DocenteResponse> getPorId(@PathVariable Long id){
        log.info("GET /docentes/:id - solicitação recebida para buscar docente com id {}.", id);
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DocenteResponse> post(@RequestBody DocenteRequest docenteRequest) throws Exception {
        log.info("POST /docentes - solicitação recebida para cadastrar novo docente");
        return ResponseEntity.ok().body(service.criarNovoDocente(docenteRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<DocenteResponse> put(
            @PathVariable Long id,
            @RequestBody DocenteRequest docenteRequest
    ) throws Exception {
        log.info("PUT /docentes/:id - solicitação recebida para atualizar cadastro de docente com id {}.", id);
        return ResponseEntity.ok().body(service.atualizar(id, docenteRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info("DELETE /docentes/:id - solicitação recebida para excluir docente com id {}.", id);
        service.excluir(id);
        return ResponseEntity.ok().body("Docente excluído com sucesso");
    }
}
