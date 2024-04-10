package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.UsuarioRequest;
import com.senai.projeto01.controller.dto.response.UsuarioResponse;
import com.senai.projeto01.datasource.entity.UsuarioEntity;
import com.senai.projeto01.datasource.repository.PapelRepository;
import com.senai.projeto01.datasource.repository.UsuarioRepository;
import com.senai.projeto01.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("/cadastro")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UsuarioResponse> novoUsuario(@RequestBody UsuarioRequest usuarioRequest) throws Exception {
        log.info("POST /cadastro - solicitação recebida para realizar cadastro de novo usuário.");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarUsuario(usuarioRequest));
    }
}
