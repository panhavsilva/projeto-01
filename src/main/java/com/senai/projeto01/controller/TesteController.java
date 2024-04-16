package com.senai.projeto01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/teste")
public class TesteController {
    @Secured("false")
    @GetMapping
    public String teste(){
        log.info("GET /teste - solicitação recebida para teste de conexão.");
        return "TESTE";
    }
}
