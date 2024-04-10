package com.senai.projeto01.datasource.repository;

import com.senai.projeto01.datasource.entity.PapelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PapelRepository extends JpaRepository<PapelEntity, Long> {
    Optional<PapelEntity> findByNome(String nome);
    @Query("SELECT p.nome FROM PapelEntity p")
    List<String> findAllPapelNames();
}
