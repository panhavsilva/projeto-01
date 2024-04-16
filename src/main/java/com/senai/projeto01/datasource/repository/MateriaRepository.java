package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    List<MateriaEntity> findByCursoId(Long cursoId);
}
