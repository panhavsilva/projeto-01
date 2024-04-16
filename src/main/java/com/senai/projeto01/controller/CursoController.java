package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.CursoRequest;
import com.senai.projeto01.controller.dto.response.CursoMateriasResponse;
import com.senai.projeto01.controller.dto.response.CursoResponse;
import com.senai.projeto01.service.CursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cursos")
public class CursoController {
    private final CursoService service;

    @GetMapping
    public ResponseEntity<List<CursoResponse>> get(){
        log.info("GET /cursos - solicitação recebida para buscar todos os cursos.");
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<CursoResponse> getPorId(@PathVariable Long id){
        log.info("GET /cursos/:id - solicitação recebida para buscar curso com id {}.", id);
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @GetMapping("{id}/materias")
    public ResponseEntity<CursoMateriasResponse> getPorIdComMaterias(@PathVariable Long id){
        log.info(
                "GET /cursos/:id/materias - solicitação recebida para buscar curso com id {} e suas matérias.",
                id
        );
        return ResponseEntity.ok().body(service.buscarPorIdComMaterias(id));
    }

    @PostMapping
    public ResponseEntity<CursoResponse> post(@RequestBody CursoRequest cursoRequest) throws Exception {
        log.info("POST /cursos - solicitação recebida para cadastrar novo curso");
        return ResponseEntity.ok().body(service.criarNovoCurso(cursoRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<CursoResponse> put(
            @PathVariable Long id,
            @RequestBody CursoRequest cursoRequest
    ) throws Exception {
        log.info("PUT /cursos/:id - solicitação recebida para atualizar cadastro de curso com id {}.", id);
        return ResponseEntity.ok().body(service.atualizar(id, cursoRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        log.info("DELETE /cursos/:id - solicitação recebida para excluir curso com id {}.", id);
        service.excluir(id);
        return ResponseEntity.ok().body("Curso excluído com sucesso");
    }
}
