package br.unitins.topicos1.ewine.model.pedido;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "pedido_historico")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PedidoHistorico extends DefaultEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PedidoStatus status;

  @Column(nullable = false)
  private LocalDateTime data;

  @Column(nullable = false, length = 300)
  private String descricao;

  public PedidoHistorico(PedidoStatus status, String descricao) {
    if (status == null) {
      throw new IllegalArgumentException("Status e obrigatorio");
    }
    if (descricao == null || descricao.isBlank()) {
      throw new IllegalArgumentException("Descricao e obrigatoria");
    }

    this.status = status;
    this.descricao = descricao.trim();
    this.data = LocalDateTime.now();
  }
}
