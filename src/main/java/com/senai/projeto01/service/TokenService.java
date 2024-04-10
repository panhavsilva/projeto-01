package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.LoginRequest;
import com.senai.projeto01.datasource.entity.UsuarioEntity;
import com.senai.projeto01.datasource.repository.UsuarioRepository;
import com.senai.projeto01.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final BCryptPasswordEncoder bCryptEncoder;
    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;
    private static long TEMPO_EXPIRACAO = 36000L;

    public String logar(LoginRequest loginRequest) throws Exception {
        log.info("Logando usuário.");
        UsuarioEntity usuarioEntity = usuarioRepository.findByNomeUsuario(loginRequest.nomeUsuario()).orElseThrow(
                () -> {
                    log.error("Nem usuário encontrado com o nome de usuário informado.");
                    return new NotFoundException("Erro ao Logar, usuário não cadastrado.");
                }
        );

        if (!usuarioEntity.senhaValida(loginRequest, bCryptEncoder)) {
            log.error("Erro ao Logar, senha incorreta.");
            throw new BadRequestException("Erro ao Logar, senha incorreta.");
        }

        Instant now = Instant.now();

        String scope = usuarioEntity.getPapel().getNome();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO))
                .subject(usuarioEntity.getId().toString())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
