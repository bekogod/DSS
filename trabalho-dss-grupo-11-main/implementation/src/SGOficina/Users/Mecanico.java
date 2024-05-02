package SGOficina.Users;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import SGOficina.*;

public class Mecanico extends Utilizador {

  private Set<Servico> competencias;

  public Mecanico(String nome, String password) {
    super(nome, password);
  }

  public Mecanico(
    String nome,
    String password,
    Set<Servico> competencias
  ) {
    super(nome, password);
    this.competencias = new HashSet<>();
  }

  public Set<Servico> getCompetencias() {
    return competencias;
  }

  public void addCompetencia(Servico servico) {
    competencias.add(servico);
  }
}
