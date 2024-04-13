package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.MateriaRequest;
import com.senai.projeto01.controller.dto.response.CursoResponse;
import com.senai.projeto01.controller.dto.response.MateriaResponse;
import com.senai.projeto01.datasource.entity.CursoEntity;
import com.senai.projeto01.datasource.entity.MateriaEntity;
import com.senai.projeto01.datasource.repository.CursoRepository;
import com.senai.projeto01.datasource.repository.MateriaRepository;
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
public class MateriaService {
    private final MateriaRepository materiaRepository;
    private final CursoService cursoService;
    private final CursoRepository cursoRepository;

    public List<MateriaResponse> buscarTodos() {
        log.info("Buscando todas os matérias.");
        List<MateriaEntity> materiaEntityList = materiaRepository.findAll();
        List<MateriaResponse> materiaResponseList = new ArrayList<>();
        for (MateriaEntity materia : materiaEntityList) {
            materiaResponseList.add(materiaResponse(materia));
        }
        log.info("Foram encontradas {} matérias.", materiaResponseList.size());
        return materiaResponseList;
    }

    public MateriaResponse buscarPorId(Long id) {
        log.info("Buscando matéria com id: {}.", id);
        MateriaEntity materiaEntity = materiaRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhuma matéria encontrada com o id: {}.", id);
                    return new NotFoundException("Nenhuma matéria encontrada com o id: " + id);
                }
        );
        MateriaResponse materia = materiaResponse(materiaEntity);
        log.info("Matéria com id: {} encontrada com sucesso.", id);
        return materia;
    }

    public MateriaResponse criarNovoMateria(MateriaRequest materiaRequest) throws Exception {
        log.info("Criando nova matéria.");
        validarDadosMateria(materiaRequest);

        MateriaEntity materia = salvarMateria(null, materiaRequest);
        log.info("Matéria criada com sucesso.");

        return materiaResponse(materia);
    }

    public MateriaResponse atualizar(Long id, MateriaRequest materiaRequest) throws Exception {
        log.info("Atualizando matéria.");
        buscarPorId(id);
        validarDadosMateria(materiaRequest);

        MateriaEntity materia = salvarMateria(id, materiaRequest);
        log.info("Matéria com id: {} atualizada com sucesso.", id);

        return materiaResponse(materia);
    }

    public void excluir(Long id) {
        log.info("Excluindo matéria com id: {}", id);
        buscarPorId(id);
        materiaRepository.deleteById(id);
        log.info("Matéria com id {} excluída sucesso.", id);
    }

    private MateriaResponse materiaResponse(MateriaEntity materia) {
        CursoEntity cursoEntity = materia.getCurso();
        CursoResponse cursoResponse = new CursoResponse(cursoEntity.getId(), cursoEntity.getNome());
        return new MateriaResponse(materia.getId(), materia.getNome(), cursoResponse);
    }

    private void validarDadosMateria(MateriaRequest materia) throws Exception {
        log.info("Validando dados da matéria.");
        if (materia.nome() == null || materia.nome().isBlank() || materia.nome().length() < 2) {
            log.error("Nome inválido.");
            throw new BadRequestException(
                    "Nome inválido, nome não pode estar em branco e tem que ter no mínimo 2 caracteres."
            );
        }
        cursoService.buscarPorId(materia.cursoId());
    }

    private MateriaEntity salvarMateria(Long id, MateriaRequest materiaRequest) throws Exception {
        CursoEntity cursoEntity = cursoRepository.findById(materiaRequest.cursoId()).orElseThrow();
        MateriaEntity materiaEntity = new MateriaEntity();
        materiaEntity.setId(id);
        materiaEntity.setNome(materiaRequest.nome());
        materiaEntity.setCurso(cursoEntity);

        return materiaRepository.save(materiaEntity);
    }
}