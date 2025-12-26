package br.unitins.topicos1.ewine.model.pedido;

public enum PedidoStatus {

  AGUARDANDO_PAGAMENTO,
  PAGO,
  ENVIADO,
  ENTREGUE,
  CANCELADO;

  public boolean podeTransicionarPara(PedidoStatus novo) {
    return switch (this) {
      case AGUARDANDO_PAGAMENTO -> novo == PAGO || novo == CANCELADO;
      case PAGO -> novo == ENVIADO || novo == CANCELADO;
      case ENVIADO -> novo == ENTREGUE;
      case ENTREGUE, CANCELADO -> false;
    };
  }

  public boolean isFinalizado() {
    return this == ENTREGUE || this == CANCELADO;
  }
}
