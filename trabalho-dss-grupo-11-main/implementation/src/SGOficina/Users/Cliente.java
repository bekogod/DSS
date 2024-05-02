package SGOficina.Users;
import java.beans.VetoableChangeListenerProxy;

import DAO.VeiculoDAO;

public class Cliente extends Utilizador {

  private int nif;
  private String morada;
  private String email;
  private int numeroTelefone;
  private VeiculoDAO veicDAO;

  public Cliente(String nome, String password) {
    super(nome, password);
  }

  public Cliente(
    String nome,
    String password,
    int nif,
    String morada,
    String email,
    int numeroTelefone
  ) {
    super(nome, password);
    this.nif = nif;
    this.morada = morada;
    this.email = email;
    this.numeroTelefone = numeroTelefone;
  }

  public int getNif() {
    return nif;
  }

  public void setNif(int nif) {
    this.nif = nif;
  }

  public String getMorada() {
    return morada;
  }

  public void setMorada(String morada) {
    this.morada = morada;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getNumeroTelefone() {
    return numeroTelefone;
  }

  public void setNumeroTelefone(int numeroTelefone) {
    this.numeroTelefone = numeroTelefone;
  }
}
