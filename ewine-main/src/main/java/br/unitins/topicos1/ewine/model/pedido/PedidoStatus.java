package br.unitins.topicos1.ewine.model.pedido;

public enum PedidoStatus {

  AGUARDANDO_PAGAMENTO,
  PAGO,
  EM_SEPARACAO,
  SAIU_PARA_ENTREGA,
  ENVIADO,
  ENTREGUE,
  CANCELADO;

  public boolean podeTransicionarPara(PedidoStatus novo) {
    return switch (this) {
      case AGUARDANDO_PAGAMENTO -> novo == PAGO || novo == CANCELADO;
      case PAGO -> novo == EM_SEPARACAO || novo == ENVIADO || novo == SAIU_PARA_ENTREGA || novo == CANCELADO;
      case EM_SEPARACAO -> novo == SAIU_PARA_ENTREGA || novo == CANCELADO;
      case SAIU_PARA_ENTREGA, ENVIADO -> novo == ENTREGUE;
      case ENTREGUE, CANCELADO -> false;
    };
  }

  public boolean isFinalizado() {
    return this == ENTREGUE || this == CANCELADO;
  }
}
