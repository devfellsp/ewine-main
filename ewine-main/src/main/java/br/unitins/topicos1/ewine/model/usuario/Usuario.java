package br.unitins.topicos1.ewine.model.usuario;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usuario extends DefaultEntity {

  @Column(nullable = false)
  private String nome;

  @Column(unique = true, nullable = false)
  private String login;

  @Column(nullable = false)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Perfil perfil;

  @Column(nullable = false)
  private boolean ativo;

  public Usuario(String nome, String login, String senha, Perfil perfil) {
    this.setNome(nome);
    this.setLogin(login);
    this.setSenha(senha);
    this.setPerfil(perfil);
    this.ativo = true;
  }

  public void atualizar(String nome) {
    this.setNome(nome);
  }

  public void alterarSenha(String novaSenha) {
    this.setSenha(novaSenha);
  }

  public void alterarPerfil(Perfil novoPerfil) {
    this.setPerfil(novoPerfil);
  }

  public void ativar() {
    this.ativo = true;
  }

  public void desativar() {
    this.ativo = false;
  }

  public boolean isAdmin() {
    return this.perfil == Perfil.ADMIN;
  }

  private void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }

    if (nome.trim().length() < 3) {
      throw new IllegalArgumentException("Nome deve ter no mínimo 3 caracteres");
    }

    this.nome = nome.trim();
  }

  private void setLogin(String login) {
    if (login == null || login.isBlank()) {
      throw new IllegalArgumentException("Login é obrigatório");
    }

    if (login.trim().length() < 4) {
      throw new IllegalArgumentException("Login deve ter no mínimo 4 caracteres");
    }

    if (!login.matches("^[a-zA-Z0-9_]+$")) {
      throw new IllegalArgumentException("Login deve conter apenas letras, números e underscore");
    }

    this.login = login.trim().toLowerCase();
  }

  private void setSenha(String senha) {
    if (senha == null || senha.isBlank()) {
      throw new IllegalArgumentException("Senha é obrigatória");
    }

    if (senha.length() < 6) {
      throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
    }

    this.senha = senha;
  }

  private void setPerfil(Perfil perfil) {
    if (perfil == null) {
      throw new IllegalArgumentException("Perfil é obrigatório");
    }

    this.perfil = perfil;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return Objects.equals(login, usuario.login);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(login);
  }
}
