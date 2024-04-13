package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
}
