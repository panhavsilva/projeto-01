package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.AlunoEntity;
import com.senai.projeto01.datasource.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
    AlunoEntity findByUsuario_Id(Long usuarioId);
}
