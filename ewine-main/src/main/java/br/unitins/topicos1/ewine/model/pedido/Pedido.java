package br.unitins.topicos1.ewine.model.pedido;

import br.unitins.topicos1.ewine.model.pedido.pagamento.FormaPagamento;
import br.unitins.topicos1.ewine.model.pedido.pagamento.Pagamento;
import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import br.unitins.topicos1.ewine.model.usuario.cliente.Cliente;
import br.unitins.topicos1.ewine.model.usuario.cliente.Endereco;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "pedido")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pedido extends DefaultEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id", nullable = false, updatable = false)
  private Cliente cliente;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "endereco_entrega_id", nullable = false, updatable = false)
  private Endereco enderecoEntrega;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "pedido_id", nullable = false, updatable = false)
  private List<ItemPedido> itens;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "pagamento_id")
  private Pagamento pagamento;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PedidoStatus status;

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime dataCriacao;

  @Column(name = "data_finalizacao")
  private LocalDateTime dataFinalizacao;

  public Pedido(
      int parcelas,
      FormaPagamento formaPagamento,
      Cliente cliente,
      Endereco enderecoEntrega,
      List<ItemPedido> itens) {

    if (cliente == null) {
      throw new IllegalArgumentException("Cliente não pode ser nulo");
    }

    if (enderecoEntrega == null) {
      throw new IllegalArgumentException("Endereço de entrega não pode ser nulo");
    }

    if (itens == null || itens.isEmpty()) {
      throw new IllegalArgumentException("Pedido deve ter pelo menos um item");
    }

    this.cliente = cliente;
    this.enderecoEntrega = enderecoEntrega;
    this.itens = new ArrayList<>(itens);
    this.status = PedidoStatus.AGUARDANDO_PAGAMENTO;
    this.dataCriacao = LocalDateTime.now();

    if (parcelas == 1) {
      this.pagamento = Pagamento.aVista(this.getTotal(), formaPagamento);
    } else {
      this.pagamento = Pagamento.parcelado(this.getTotal(), parcelas, formaPagamento);
    }
  }

  public void confirmarPagamento(String transacaoExternaId) {
    if (this.pagamento == null || !this.pagamento.isPendente()) {
      throw new IllegalStateException("Não há pagamento pendente");
    }

    this.status = PedidoStatus.PAGO;
    this.pagamento.confirmar(transacaoExternaId);
    this.baixarEstoque();
  }

  public void recusarPagamento() {
    if (this.pagamento == null || !this.pagamento.isPendente()) {
      throw new IllegalStateException("Não há pagamento pendente");
    }

    this.pagamento.recusar();
  }

  public void enviar() {
    if (!this.status.podeTransicionarPara(PedidoStatus.ENVIADO)) {
      throw new IllegalStateException("Pedido não pode ser enviado no status atual");
    }

    this.status = PedidoStatus.ENVIADO;
  }

  public void confirmarEntrega() {
    if (!this.status.podeTransicionarPara(PedidoStatus.ENTREGUE)) {
      throw new IllegalStateException("Pedido não pode ser entregue no status atual");
    }

    this.status = PedidoStatus.ENTREGUE;
    this.dataFinalizacao = LocalDateTime.now();
  }

  public void cancelar() {
    if (!this.status.podeTransicionarPara(PedidoStatus.CANCELADO)) {
      throw new IllegalStateException("Pedido não pode ser cancelado no status atual");
    }

    this.status = PedidoStatus.CANCELADO;
    this.dataFinalizacao = LocalDateTime.now();
    this.estornarEstoque();
  }

  public double getTotal() {
    return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
  }

  public int getQuantidadeItens() {
    return itens.stream().mapToInt(ItemPedido::getQuantidade).sum();
  }

  public List<ItemPedido> getItens() {
    return Collections.unmodifiableList(itens);
  }

  public boolean isPago() {
    return pagamento != null && pagamento.isConfirmado();
  }

  public boolean isAguardandoConfirmacaoPagamento() {
    return pagamento != null && pagamento.isPendente();
  }

  private void baixarEstoque() {
    for (ItemPedido item : itens) {
      item.getProduto().diminuirEstoque(item.getQuantidade());
    }
  }

  private void estornarEstoque() {
    for (ItemPedido item : itens) {
      item.getProduto().aumentarEstoque(item.getQuantidade());
    }
  }
}
