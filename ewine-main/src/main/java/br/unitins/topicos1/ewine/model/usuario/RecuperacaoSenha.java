package br.unitins.topicos1.ewine.model.usuario;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "recuperacao_senha")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecuperacaoSenha extends DefaultEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Usuario usuario;

  @Column(unique = true, nullable = false)
  private String token;

  @Column(name = "data_expiracao", nullable = false)
  private LocalDateTime dataExpiracao;

  @Column(nullable = false)
  private boolean usado;

  public RecuperacaoSenha(Usuario usuario) {
    if (usuario == null) {
      throw new IllegalArgumentException("Usuario e obrigatorio");
    }
    this.usuario = usuario;
    this.token = UUID.randomUUID().toString();
    this.dataExpiracao = LocalDateTime.now().plusMinutes(30);
    this.usado = false;
  }

  public boolean isValido() {
    return !usado && dataExpiracao.isAfter(LocalDateTime.now());
  }

  public void marcarComoUsado() {
    this.usado = true;
  }
}
