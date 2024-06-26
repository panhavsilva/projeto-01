package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.UsuarioRequest;
import com.senai.projeto01.controller.dto.response.UsuarioResponse;
import com.senai.projeto01.datasource.entity.PapelEntity;
import com.senai.projeto01.datasource.entity.UsuarioEntity;
import com.senai.projeto01.datasource.repository.PapelRepository;
import com.senai.projeto01.datasource.repository.UsuarioRepository;
import com.senai.projeto01.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final BCryptPasswordEncoder bCryptEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest) throws Exception {
        log.info("Cadastrando usuário.");
        boolean usuarioExistente = usuarioRepository.findByNomeUsuario(usuarioRequest.nomeUsuario())
                .isPresent();

        if (usuarioExistente){
            log.error("Usuário já cadastrado.");
            throw new BadRequestException("Usuário já cadastrado.");
        }

        PapelEntity papel =  papelRepository.findByNome(
                usuarioRequest.nomePapel().toUpperCase()
        ).orElseThrow(
                () -> {
                    List<String> papeis = papelRepository.findAllPapelNames();
                    log.error("Nenhum papel encontrado com o nome informado.");
                    return new NotFoundException(
                            "Nenhum papel encontrado com o nome informado. " +
                                    "Papeis disponíveis: " + papeis
                    );
                }
        );

        validarDadosUsuario(usuarioRequest);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(null);
        usuario.setNomeUsuario(usuarioRequest.nomeUsuario());
        usuario.setSenha(bCryptEncoder.encode(usuarioRequest.senha()).toString());
        usuario.setPapel(papel);

        UsuarioEntity novoUsuario = usuarioRepository.save(usuario);
        log.info("Usuário cadastrado com sucesso.");

        return usuarioResponse(novoUsuario);
    }

    public List<UsuarioResponse> buscarTodos() {
        log.info("Buscando todos os usuários.");
        List<UsuarioEntity> usuariosEntity = usuarioRepository.findAll();
        List<UsuarioResponse> usuarios = new ArrayList<>();
        for (UsuarioEntity usuario : usuariosEntity) {
            usuarios.add(usuarioResponse(usuario));
        }
        log.info("Foram encontrados {} usuários.", usuarios.size());
        return usuarios;
    }

    public UsuarioResponse buscarPorId(Long id) {
        log.info("Buscando usuário com id {}.", id);
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhum usuário encontrado com o id: {}.", id);
                    return new NotFoundException("Nenhum usuário encontrado com o id: " + id);
                }
        );
        UsuarioResponse usuario = usuarioResponse(usuarioEntity);
        log.info("Usuário encontrado com sucesso.");
        return usuario;
    }

    private UsuarioResponse usuarioResponse(UsuarioEntity usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNomeUsuario(), usuario.getPapel().getNome());
    }

    private void validarDadosUsuario(UsuarioRequest usuario) throws Exception {
        log.info("Validando dados do usuário.");
        if (usuario.nomeUsuario() == null|| usuario.nomeUsuario().isBlank() || usuario.nomeUsuario().length() < 3) {
            log.error("Nome de usuário inválido.");
            throw new BadRequestException(
                    "Nome de usuário inválido. "+
                    "Nome de usuário não pode estar em branco e tem que ter no mínimo 3 caracteres."
            );
        }

        if(usuario.senha() == null|| usuario.senha().isBlank() || usuario.senha().length() < 3){
            log.error("Senha inválida.");
            throw new BadRequestException(
                    "Senha inválida. "+
                    "Senha não pode estar em branco e tem que ter no mínimo 3 caracteres."
            );
        }
    }
}
