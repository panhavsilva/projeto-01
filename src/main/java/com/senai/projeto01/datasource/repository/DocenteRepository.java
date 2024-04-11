package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {
    DocenteEntity findByUsuario_Id(Long usuarioId);
}
