package com.senai.projeto01.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "turma")
public class TurmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private DocenteEntity professor;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private CursoEntity curso;
}
