package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.MateriaRequest;
import com.senai.projeto01.controller.dto.response.MateriaComCursoResponse;
import com.senai.projeto01.service.MateriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/materias")
public class MateriaController {
    private final MateriaService service;

    @GetMapping
    public ResponseEntity<List<MateriaComCursoResponse>> get(){
        log.info("GET /materias - solicitação recebida para buscar todos as matérias.");
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<MateriaComCursoResponse> getPorId(@PathVariable Long id){
        log.info("GET /materias/:id - solicitação recebida para buscar matéria com id {}.", id);
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MateriaComCursoResponse> post(@RequestBody MateriaRequest materiaRequest) throws Exception {
        log.info("POST /materias - solicitação recebida para cadastrar nova matéria");
        return ResponseEntity.ok().body(service.criarNovoMateria(materiaRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<MateriaComCursoResponse> put(
            @PathVariable Long id,
            @RequestBody MateriaRequest materiaRequest
    ) throws Exception {
        log.info("PUT /materias/:id - solicitação recebida para atualizar cadastro da matéria com id {}.", id);
        return ResponseEntity.ok().body(service.atualizar(id, materiaRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info("DELETE /materias/:id - solicitação recebida para excluir matéria com id {}.", id);
        service.excluir(id);
        return ResponseEntity.ok().body("Matéria excluído com sucesso");
    }
}
