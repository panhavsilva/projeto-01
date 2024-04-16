package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findAllByAlunoId(Long alunoId);
}
