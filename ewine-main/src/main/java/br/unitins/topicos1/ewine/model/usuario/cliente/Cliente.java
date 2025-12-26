package br.unitins.topicos1.ewine.model.usuario.cliente;

import br.unitins.topicos1.ewine.model.shared.DefaultEntity;
import br.unitins.topicos1.ewine.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Cliente extends DefaultEntity {

  @NotBlank private String cpf;

  @Email private String email;

  @OneToOne private Usuario usuario;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType. LAZY)
  @JoinColumn(name = "cliente_id")
  private List<Endereco> enderecos;

  public Cliente() {}

    public Cliente(Long id) {
    this.setId(id);
  }

  public Cliente(String cpf, String email, Usuario usuario) {
    if (cpf == null) {
      throw new IllegalArgumentException("CPF não pode ser nulo");
    }

    if (email == null) {
      throw new IllegalArgumentException("Email não pode ser nulo");
    }

    if (usuario == null) {
      throw new IllegalArgumentException("Usuário não pode ser nulo");
    }

    this.cpf = cpf;
    this.email = email;
    this.usuario = usuario;
  }

  public Endereco getEndereco(Long id) {
    return this.enderecos.stream()
        .filter(endereco -> endereco.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));
  }
  public void addEndereco(Endereco endereco) {
      if (this.enderecos == null) {
          this.enderecos = new ArrayList<>();
      }

      this.enderecos.add(endereco);
  }
}
