package br.unitins.topicos1.ewine.service.impl;

import br.unitins.topicos1.ewine.service.HashService;
import jakarta.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@ApplicationScoped
public class HashServiceImpl implements HashService {

  @Override
  public String getHashSenha(String senha) {
    try {
      String salt = "#$127732&";
      int iterationCount = 403;
      int keyLength = 512;

      byte[] result =
          SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
              .generateSecret(
                  new PBEKeySpec(senha.toCharArray(), salt.getBytes(), iterationCount, keyLength))
              .getEncoded();

      return Base64.getEncoder().encodeToString(result);

    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      throw new RuntimeException("Erro ao gerar o hash");
    }
  }

  public static void main(String[] args) {
    HashService hash = new HashServiceImpl();
    System.out.println(hash.getHashSenha("123"));
  }
}
