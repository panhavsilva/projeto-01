package com.senai.projeto01.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "nota")
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "numeric(5,2)")
    private Double nota;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private DocenteEntity professor;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private MateriaEntity materia;
}
