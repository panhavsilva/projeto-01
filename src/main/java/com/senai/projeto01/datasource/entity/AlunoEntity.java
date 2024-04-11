package com.senai.projeto01.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "aluno")
public class AlunoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private TurmaEntity turma;
}
