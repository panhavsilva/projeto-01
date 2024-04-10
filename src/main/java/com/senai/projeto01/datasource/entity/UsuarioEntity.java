package com.senai.projeto01.datasource.entity;

import com.senai.projeto01.controller.dto.request.LoginRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_usuario", length = 50, nullable = false, unique = true)
    private String nomeUsuario;

    @Column(length = 100, nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "papel_id")
    private PapelEntity papel;

    public boolean senhaValida(LoginRequest loginRequest, BCryptPasswordEncoder bCryptEncoder) {
        return bCryptEncoder.matches(loginRequest.senha(), this.senha);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) this.getPapel();
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getNomeUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
