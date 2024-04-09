package com.senai.projeto01.controller;

import com.senai.projeto01.controller.dto.request.LoginRequest;
import com.senai.projeto01.controller.dto.response.LoginResponse;
import com.senai.projeto01.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService service;
    private static long TEMPO_EXPIRACAO = 36000L;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> token(@RequestBody LoginRequest loginRequest) throws Exception {
        var valorJWT = service.logar(loginRequest);

        return ResponseEntity.ok(new LoginResponse(valorJWT, TEMPO_EXPIRACAO));
    }
}
