package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.AlunoRequest;
import com.senai.projeto01.controller.dto.response.*;
import com.senai.projeto01.datasource.entity.*;
import com.senai.projeto01.datasource.repository.AlunoRepository;
import com.senai.projeto01.datasource.repository.TurmaRepository;
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
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final TurmaService turmaService;
    private final TurmaRepository turmaRepository;

    public List<AlunoResponse> buscarTodos() {
        log.info("Buscando todos os alunos.");
        List<AlunoEntity> alunoEntityList = alunoRepository.findAll();
        List<AlunoResponse> alunoResponseList = new ArrayList<>();
        for (AlunoEntity aluno : alunoEntityList) {
            alunoResponseList.add(alunoResponse(aluno));
        }
        log.info("Foram encontrados {} alunos.", alunoResponseList.size());
        return alunoResponseList;
    }

    public AlunoResponse buscarPorId(Long id) {
        log.info("Buscando aluno com id: {}.", id);
        AlunoEntity alunoEntity = alunoRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhum aluno encontrado com o id: {}.", id);
                    return new NotFoundException("Nenhum aluno encontrado com o id: " + id);
                }
        );
        AlunoResponse aluno = alunoResponse(alunoEntity);
        log.info("Aluno com id: {} encontrado com sucesso.", id);
        return aluno;
    }

    public AlunoResponse criarNovoAluno(AlunoRequest alunoRequest) throws Exception {
        log.info("Criando novo aluno.");
        usuarioEhAluno(alunoRequest.usuarioId());
        validarDadosAluno(alunoRequest);

        AlunoEntity aluno = salvarAluno(null, alunoRequest);
        log.info("Aluno criado com sucesso.");

        return alunoResponse(aluno);
    }

    public AlunoResponse atualizar(Long id, AlunoRequest alunoRequest) throws Exception {
        log.info("Atualizando aluno.");
        buscarPorId(id);
        validarDadosAluno(alunoRequest);

        AlunoEntity aluno = salvarAluno(id, alunoRequest);
        log.info("Aluno com id: {} atualizado com sucesso.", id);

        return alunoResponse(aluno);
    }

    public void excluir(Long id) {
        log.info("Excluindo aluno com id: {}", id);
        buscarPorId(id);
        alunoRepository.deleteById(id);
        log.info("Aluno com id {} excluído sucesso.", id);
    }

    private AlunoResponse alunoResponse(AlunoEntity aluno) {
        TurmaEntity turmaEntity = aluno.getTurma();
        ProfessorResponse professor = new ProfessorResponse(
                turmaEntity.getProfessor().getId(),
                turmaEntity.getProfessor().getNome()
        );
        CursoResponse curso = new CursoResponse(
                turmaEntity.getCurso().getId(),
                turmaEntity.getCurso().getNome()
        );
        TurmaResponse turma = new TurmaResponse(
                turmaEntity.getId(),
                turmaEntity.getNome(),
                professor,
                curso
        );

        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getDataNascimento(),
                aluno.getUsuario().getNomeUsuario(),
                turma
        );
    }

    private void validarDadosAluno(AlunoRequest aluno) throws Exception {
        log.info("Validando dados do aluno.");
        validarNome(aluno.nome());
        validarDataNascimento(aluno.dataNascimento());
        validarPapelUsuario(aluno.usuarioId());

        turmaService.buscarPorId(aluno.turmaId());
    }

    private void validarNome(String nome) throws Exception {
        if (nome == null || nome.isBlank() || nome.length() < 5) {
            log.error("Nome inválido.");
            throw new BadRequestException(
                    "Nome inválido, nome não pode estar em branco e tem que ter no mínimo 5 caracteres."
            );
        }
    }

    private void validarDataNascimento(String dataNascimentoString) throws Exception {
        LocalDate dataNascimento = Data.converterStringParaData(dataNascimentoString);
        LocalDate dataAtual = LocalDate.now();
        if (dataNascimento.isAfter(dataAtual)) {
            log.error("Data de nascimento inválida.");
            throw new BadRequestException(
                    "Data de nascimento inválida. "+
                    "Data de nascimento não pode ser posterior ao dia atual."
            );
        }
    }

    private void validarPapelUsuario(Long usuarioId) throws Exception {
        UsuarioResponse usuario = usuarioService.buscarPorId(usuarioId);
        if (!usuario.papel().equals("ALUNO")){
            log.error("O papel do usuário deve estar como aluno para ser cadastrado como aluno.");
            throw new BadRequestException(
                    "Usuário não pode ser cadastrado como aluno. "+
                    "O papel do usuário deve estar como aluno para ser cadastrado como aluno."
            );
        }
    }

    private void usuarioEhAluno(Long usuarioId) throws Exception {
        AlunoEntity ehCadastrado = alunoRepository.findByUsuario_Id(usuarioId);
        if (ehCadastrado != null){
            log.error("O usuário informado já está cadastrado.");
            throw new BadRequestException(
                    "O usuário informado já está cadastrado."
            );
        }
    }

    private AlunoEntity salvarAluno(Long id, AlunoRequest alunoRequest) throws Exception {
        UsuarioEntity usuario = usuarioRepository.findById(alunoRequest.usuarioId()).orElseThrow();
        TurmaEntity turma = turmaRepository.findById(alunoRequest.turmaId()).orElseThrow();

        AlunoEntity alunoEntity = new AlunoEntity();
        alunoEntity.setId(id);
        alunoEntity.setNome(alunoRequest.nome());
        alunoEntity.setDataNascimento(Data.converterStringParaData(alunoRequest.dataNascimento()));
        alunoEntity.setUsuario(usuario);
        alunoEntity.setTurma(turma);

        return alunoRepository.save(alunoEntity);
    }
}
