package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {
}
