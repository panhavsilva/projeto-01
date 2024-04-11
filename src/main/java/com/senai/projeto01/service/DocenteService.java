package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.DocenteRequest;
import com.senai.projeto01.controller.dto.response.DocenteResponse;
import com.senai.projeto01.controller.dto.response.UsuarioResponse;
import com.senai.projeto01.datasource.entity.DocenteEntity;
import com.senai.projeto01.datasource.repository.DocenteRepository;
import com.senai.projeto01.datasource.repository.UsuarioRepository;
import com.senai.projeto01.exception.NotFoundException;
import com.senai.projeto01.utils.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocenteService {
    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public List<DocenteResponse> buscarTodos() {
        log.info("Buscando todos os docentes.");
        List<DocenteEntity> docenteEntityList = docenteRepository.findAll();
        List<DocenteResponse> docenteResponseList = new ArrayList<>();
        for (DocenteEntity docente : docenteEntityList){
            docenteResponseList.add(docenteResponse(docente));
        }
        log.info("Foram encontrados {} docentes.", docenteResponseList.size());
        return docenteResponseList;
    }

    public DocenteResponse buscarPorId(Long id) {
        log.info("Buscando docente com id: {}.", id);
        DocenteEntity docenteEntity = docenteRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhum docente encontrado com o id: {}.", id);
                    return new NotFoundException("Nenhum docente encontrado com o id: " + id);
                }
        );
        DocenteResponse docente = docenteResponse(docenteEntity);
        log.info("Docente com id: {} encontrado com sucesso.", id);
        return docente;
    }

    public DocenteResponse criarNovoDocente(DocenteRequest docenteRequest) throws Exception {
        log.info("Criando novo docente.");
        usuarioEhDocente(docenteRequest.usuarioId());
        validarDadosDocente(docenteRequest);

        DocenteEntity docente = salvarDocente(null, docenteRequest);
        log.info("Docente criado com sucesso.");

        return docenteResponse(docente);
    }

    public DocenteResponse atualizar(Long id, DocenteRequest docenteRequest) throws Exception {
        log.info("Atualizando docente.");
        buscarPorId(id);
        validarDadosDocente(docenteRequest);

        DocenteEntity docente = salvarDocente(id, docenteRequest);
        log.info("Docente atualizado com sucesso.");

        return docenteResponse(docente);
    }

    public void excluir(Long id) {
        log.info("Excluindo docente com id: {}", id);
        buscarPorId(id);
        docenteRepository.deleteById(id);
        log.info("Docente com id {} excluído sucesso.", id);
    }

    private DocenteResponse docenteResponse(DocenteEntity docente) {
        return new DocenteResponse(
                docente.getId(),
                docente.getNome(),
                docente.getDataEntrada(),
                docente.getUsuario().getNomeUsuario(),
                docente.getUsuario().getPapel().getNome()
        );
    }

    private DocenteEntity salvarDocente(Long id, DocenteRequest docenteRequest) throws Exception {
        DocenteEntity docenteEntity = new DocenteEntity();
        docenteEntity.setId(id);
        docenteEntity.setNome(docenteRequest.nome());
        docenteEntity.setDataEntrada(Data.converterStringParaData(docenteRequest.dataEntrada()));
        docenteEntity.setUsuario(usuarioRepository.findById(docenteRequest.usuarioId()).orElseThrow());

        return docenteRepository.saveAndFlush(docenteEntity);
    }

    private void usuarioEhDocente(Long usuarioId) throws Exception {
        DocenteEntity ehCadastrado = docenteRepository.findByUsuario_Id(usuarioId);
        if (ehCadastrado != null){
            log.error("O usuário informado já está cadastrado.");
            throw new BadRequestException(
                    "O usuário informado já está cadastrado."
            );
        }
    }

    private void validarDadosDocente(DocenteRequest docente) throws Exception {
        log.info("Validando dados do docente.");
        if (docente.nome() == null|| docente.nome().isBlank() || docente.nome().length() < 8) {
            log.error("Nome inválido.");
            throw new BadRequestException(
                    "Nome inválido, deve ser informado o nome completo do docente. "+
                    "Nome não pode estar em branco e tem que ter no mínimo 8 caracteres."
            );
        }

        LocalDate dataEntrada = Data.converterStringParaData(docente.dataEntrada());
        LocalDate dataAtual = LocalDate.now();
        if (dataEntrada.isAfter(dataAtual)) {
            log.error("Data de entrada inválida.");
            throw new BadRequestException(
                    "Data de entrada inválida. "+
                    "Data de entrada não pode ser posterior ao dia atual."
            );
        }

        UsuarioResponse usuario = usuarioService.buscarPorId(docente.usuarioId());
        if (usuario.papel().equals("ALUNO")){
            log.error("Data de entrada inválida.");
            throw new BadRequestException(
                    "Usuário não pode ser cadastrado como docente. "+
                    "O papel deste usuário está como aluno e não pode ser cadastrado como docente."
            );
        }
    }
}
