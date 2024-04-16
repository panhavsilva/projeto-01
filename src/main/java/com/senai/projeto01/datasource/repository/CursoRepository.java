package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<CursoEntity, Long> {
}
