package com.senai.projeto01.service;

import com.senai.projeto01.controller.dto.request.NotaRequest;
import com.senai.projeto01.controller.dto.response.*;
import com.senai.projeto01.datasource.entity.*;
import com.senai.projeto01.datasource.repository.AlunoRepository;
import com.senai.projeto01.datasource.repository.DocenteRepository;
import com.senai.projeto01.datasource.repository.MateriaRepository;
import com.senai.projeto01.datasource.repository.NotaRepository;
import com.senai.projeto01.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotaService {
    private final NotaRepository notaRepository;
    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;
    private final DocenteService docenteService;
    private final DocenteRepository docenteRepository;
    private final MateriaService materiaService;
    private final MateriaRepository materiaRepository;

    public List<NotaResponse> buscarTodos() {
        log.info("Buscando todas os notas.");
        List<NotaEntity> notaEntityList = notaRepository.findAll();
        List<NotaResponse> notaResponseList = new ArrayList<>();
        for (NotaEntity nota : notaEntityList) {
            notaResponseList.add(notaResponse(nota));
        }
        log.info("Foram encontradas {} notas.", notaResponseList.size());
        return notaResponseList;
    }

    public NotaResponse buscarPorId(Long id) {
        log.info("Buscando nota com id: {}.", id);
        NotaEntity notaEntity = notaRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Nenhuma nota encontrada com o id: {}.", id);
                    return new NotFoundException("Nenhuma nota encontrada com o id: " + id);
                }
        );
        NotaResponse nota = notaResponse(notaEntity);
        log.info("Nota com id: {} encontrada com sucesso.", id);
        return nota;
    }

    public NotaResponse criarNovoNota(NotaRequest notaRequest) throws Exception {
        log.info("Criando nova nota.");
        validarDadosNota(notaRequest);

        LocalDate data = LocalDate.now();
        NotaEntity nota = salvarNota(null, notaRequest, data);
        log.info("Nota criada com sucesso.");

        return notaResponse(nota);
    }

    public NotaResponse atualizar(Long id, NotaRequest notaRequest) throws Exception {
        log.info("Atualizando nota.");
        NotaResponse notaResponse = buscarPorId(id);
        validarDadosNota(notaRequest);

        NotaEntity nota = salvarNota(id, notaRequest, notaResponse.data());
        log.info("Nota com id: {} atualizada com sucesso.", id);

        return notaResponse(nota);
    }

    public void excluir(Long id) {
        log.info("Excluindo nota com id: {}", id);
        buscarPorId(id);
        notaRepository.deleteById(id);
        log.info("Nota com id {} excluída com sucesso.", id);
    }

    private NotaResponse notaResponse(NotaEntity nota) {
        AlunoResponse aluno = alunoResponse(nota);
        MateriaResponse materia = materiaResponse(nota);
        return new NotaResponse(
                nota.getId(),
                nota.getNota(),
                nota.getData(),
                aluno,
                materia
        );
    }

    private CursoResponse cursoResponse(NotaEntity nota) {
        return new CursoResponse(
                nota.getAluno().getTurma().getCurso().getId(),
                nota.getAluno().getTurma().getCurso().getNome()
        );
    }

    private ProfessorResponse professorResponse(NotaEntity nota) {
        return new ProfessorResponse(
                nota.getAluno().getTurma().getProfessor().getId(),
                nota.getAluno().getTurma().getProfessor().getNome()
        );
    }

    private TurmaResponse turmaResponse(NotaEntity nota) {
        return new TurmaResponse(
                nota.getAluno().getTurma().getId(),
                nota.getAluno().getTurma().getNome(),
                professorResponse(nota),
                cursoResponse(nota)
        );
    }

    private MateriaResponse materiaResponse(NotaEntity nota) {
        return new MateriaResponse(
                nota.getMateria().getId(),
                nota.getMateria().getNome()
        );
    }

    private AlunoResponse alunoResponse(NotaEntity nota) {
        return new AlunoResponse(
                nota.getAluno().getId(),
                nota.getAluno().getNome(),
                nota.getAluno().getDataNascimento(),
                nota.getAluno().getUsuario().getNomeUsuario(),
                turmaResponse(nota)
        );
    }

    private void validarDadosNota(NotaRequest notaRequest) throws Exception {
        log.info("Validando dados da nota.");
        if (notaRequest.nota() < 0) {
            log.error("Nota inválida. Nota deve ser igual ou maior que 0.");
            throw new BadRequestException(
                    "Nota inválida. Nota deve ser igual ou maior que 0."
            );
        }
        alunoService.buscarPorId(notaRequest.alunoId());
        materiaService.buscarPorId(notaRequest.materiaId());

        validarMateria(notaRequest);
    }

    private void validarMateria(NotaRequest notaRequest) throws Exception {
        AlunoEntity aluno = alunoRepository.findById(notaRequest.alunoId()).orElseThrow();
        TurmaEntity turma = aluno.getTurma();
        CursoEntity curso = turma.getCurso();
        List<MateriaEntity> materiaEntityList = materiaRepository.findByCursoId(curso.getId());
        List<Long> materiasId = new ArrayList<>();
        for (MateriaEntity materia : materiaEntityList){
            materiasId.add(materia.getId());
        }

        if(!materiasId.contains(notaRequest.materiaId())){
            log.error("Id da matéria é inválido. Esta matéria não está no curso do aluno.");
            throw new BadRequestException(
                    "Id da matéria é inválido. Esta matéria não está no curso do aluno." +
                    "Segue Id das matérias do curso do aluno: " + materiasId
            );
        }
    }

    private NotaEntity salvarNota(Long id, NotaRequest notaRequest, LocalDate data) throws Exception {
        AlunoEntity aluno = alunoRepository.findById(notaRequest.alunoId()).orElseThrow();
        TurmaEntity turma = aluno.getTurma();
        DocenteEntity professor = turma.getProfessor();
        MateriaEntity materia = materiaRepository.findById(notaRequest.materiaId()).orElseThrow();

        NotaEntity notaEntity = new NotaEntity();
        notaEntity.setId(id);
        notaEntity.setData(data);
        notaEntity.setNota(notaRequest.nota());
        notaEntity.setAluno(aluno);
        notaEntity.setProfessor(professor);
        notaEntity.setMateria(materia);

        return notaRepository.save(notaEntity);
    }
}
