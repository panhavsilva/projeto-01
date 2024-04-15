package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.TurmaRequest;
import com.senai.projeto01.controller.dto.response.CursoResponse;
import com.senai.projeto01.controller.dto.response.ProfessorResponse;
import com.senai.projeto01.controller.dto.response.TurmaResponse;
import com.senai.projeto01.datasource.entity.CursoEntity;
import com.senai.projeto01.datasource.entity.DocenteEntity;
import com.senai.projeto01.datasource.entity.TurmaEntity;
import com.senai.projeto01.datasource.repository.CursoRepository;
import com.senai.projeto01.datasource.repository.DocenteRepository;
import com.senai.projeto01.datasource.repository.TurmaRepository;
import com.senai.projeto01.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final CursoService cursoService;
    private final DocenteService docenteService;
    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;

    public List<TurmaResponse> buscarTodos() {
        log.info("Buscando todas os turmas.");
        List<TurmaEntity> turmaEntityList = turmaRepository.findAll();
        List<TurmaResponse> turmaResponseList = new ArrayList<>();
        for (TurmaEntity turma : turmaEntityList) {
            turmaResponseList.add(turmaResponse(turma));
        }
        log.info("Foram encontradas {} turmas.", turmaResponseList.size());
        return turmaResponseList;
    }

    public TurmaResponse buscarPorId(Long id) {
        log.info("Buscando turma com id: {}.", id);
        TurmaEntity turmaEntity = turmaRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhuma turma encontrada com o id: {}.", id);
                    return new NotFoundException("Nenhuma turma encontrada com o id: " + id);
                }
        );
        TurmaResponse turma = turmaResponse(turmaEntity);
        log.info("Turma com id: {} encontrada com sucesso.", id);
        return turma;
    }

    public TurmaResponse criarNovoTurma(TurmaRequest turmaRequest) throws Exception {
        log.info("Criando nova turma.");
        validarDadosTurma(turmaRequest);

        TurmaEntity turma = salvarTurma(null, turmaRequest);
        log.info("Turma criada com sucesso.");

        return turmaResponse(turma);
    }

    public TurmaResponse atualizar(Long id, TurmaRequest turmaRequest) throws Exception {
        log.info("Atualizando turma.");
        buscarPorId(id);
        validarDadosTurma(turmaRequest);

        TurmaEntity turma = salvarTurma(id, turmaRequest);
        log.info("Turma com id: {} atualizada com sucesso.", id);

        return turmaResponse(turma);
    }

    public void excluir(Long id) {
        log.info("Excluindo turma com id: {}", id);
        buscarPorId(id);
        turmaRepository.deleteById(id);
        log.info("Turma com id {} excluída com sucesso.", id);
    }

    private TurmaResponse turmaResponse(TurmaEntity turma) {
        CursoEntity cursoEntity = turma.getCurso();
        CursoResponse cursoResponse = new CursoResponse(cursoEntity.getId(), cursoEntity.getNome());
        DocenteEntity docenteEntity = turma.getProfessor();
        ProfessorResponse professorResponse = new ProfessorResponse(docenteEntity.getId(), docenteEntity.getNome());
        return new TurmaResponse(turma.getId(), turma.getNome(), professorResponse, cursoResponse);
    }

    private void validarDadosTurma(TurmaRequest turma) throws Exception {
        log.info("Validando dados da turma.");
        if (turma.nome() == null || turma.nome().isBlank() || turma.nome().length() < 2) {
            log.error("Nome inválido.");
            throw new BadRequestException(
                    "Nome inválido, nome não pode estar em branco e tem que ter no mínimo 2 caracteres."
            );
        }
        cursoService.buscarPorId(turma.cursoId());
    }

    private TurmaEntity salvarTurma(Long id, TurmaRequest turmaRequest) throws Exception {
        CursoEntity cursoEntity = cursoRepository.findById(turmaRequest.cursoId()).orElseThrow();
        DocenteEntity docenteEntity = docenteRepository.findById(turmaRequest.professorId()).orElseThrow();
        TurmaEntity turmaEntity = new TurmaEntity();
        turmaEntity.setId(id);
        turmaEntity.setNome(turmaRequest.nome());
        turmaEntity.setCurso(cursoEntity);
        turmaEntity.setProfessor(docenteEntity);

        return turmaRepository.save(turmaEntity);
    }
}
