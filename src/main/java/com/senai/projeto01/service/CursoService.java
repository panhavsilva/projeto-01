package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.CursoRequest;
import com.senai.projeto01.controller.dto.response.CursoMateriasResponse;
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
public class CursoService {
    private final CursoRepository cursoRepository;
    private final MateriaRepository materiaRepository;

    public List<CursoResponse> buscarTodos() {
        log.info("Buscando todos os cursos.");
        List<CursoEntity> cursoEntityList = cursoRepository.findAll();
        List<CursoResponse> cursoResponseList = new ArrayList<>();
        for (CursoEntity curso : cursoEntityList) {
            cursoResponseList.add(cursoResponse(curso));
        }
        log.info("Foram encontrados {} cursos.", cursoResponseList.size());
        return cursoResponseList;
    }

    public CursoResponse buscarPorId(Long id) {
        log.info("Buscando curso com id: {}.", id);
        CursoEntity cursoEntity = cursoRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhum curso encontrado com o id: {}.", id);
                    return new NotFoundException("Nenhum curso encontrado com o id: " + id);
                }
        );
        CursoResponse curso = cursoResponse(cursoEntity);
        log.info("Curso com id: {} encontrado com sucesso.", id);
        return curso;
    }

    public CursoMateriasResponse buscarPorIdComMaterias(Long id) {
        log.info("Buscando curso com id: {}.", id);
        CursoEntity cursoEntity = cursoRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhum curso encontrado com o id: {}.", id);
                    return new NotFoundException("Nenhum curso encontrado com o id: " + id);
                }
        );
        CursoMateriasResponse curso = cursoMateriasResponse(cursoEntity);
        log.info("Curso com id: {} encontrado com sucesso.", id);
        return curso;
    }

    public CursoResponse criarNovoCurso(CursoRequest cursoRequest) throws Exception {
        log.info("Criando novo curso.");
        validarDadosCurso(cursoRequest);

        CursoEntity curso = salvarCurso(null, cursoRequest);
        log.info("Curso criado com sucesso.");

        return cursoResponse(curso);
    }

    public CursoResponse atualizar(Long id, CursoRequest cursoRequest) throws Exception {
        log.info("Atualizando curso.");
        buscarPorId(id);
        validarDadosCurso(cursoRequest);

        CursoEntity curso = salvarCurso(id, cursoRequest);
        log.info("Curso com id: {} atualizado com sucesso.", id);

        return cursoResponse(curso);
    }

    public void excluir(Long id) {
        log.info("Excluindo curso com id: {}", id);
        buscarPorId(id);
        cursoRepository.deleteById(id);
        log.info("Curso com id {} excluído sucesso.", id);
    }

    private CursoResponse cursoResponse(CursoEntity curso) {
        return new CursoResponse(curso.getId(), curso.getNome());
    }

    private CursoMateriasResponse cursoMateriasResponse(CursoEntity curso) {
        List<MateriaEntity> materias = materiaRepository.findByCursoId(curso.getId());
        List<MateriaResponse> materiaResponseList = new ArrayList<>();
        for (MateriaEntity materia : materias) {
            materiaResponseList.add(
                    new MateriaResponse(materia.getId(), materia.getNome())
            );
        }
        return new CursoMateriasResponse(
                curso.getId(),
                curso.getNome(),
                materiaResponseList
        );
    }

    private void validarDadosCurso(CursoRequest curso) throws Exception {
        log.info("Validando dados do curso.");
        if (curso.nome() == null || curso.nome().isBlank() || curso.nome().length() < 2) {
            log.error("Nome inválido.");
            throw new BadRequestException(
                    "Nome inválido, nome não pode estar em branco e tem que ter no mínimo 2 caracteres."
            );
        }
    }

    private CursoEntity salvarCurso(Long id, CursoRequest cursoRequest) throws Exception {
        CursoEntity cursoEntity = new CursoEntity();
        cursoEntity.setId(id);
        cursoEntity.setNome(cursoRequest.nome());

        return cursoRepository.saveAndFlush(cursoEntity);
    }
}
