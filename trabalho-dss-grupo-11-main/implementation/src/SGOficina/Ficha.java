package SGOficina;
import java.util.ArrayList;
import java.util.List;

public class Ficha {

  private List<Servico> historico;

  public Ficha() {
    this.historico = new ArrayList<>();
  }

  public List<Servico> getHistorico() {
    return this.historico;
  }

  public void setHistorico(List<Servico> historico) {
    this.historico = historico;
  }

  public void addHistorico(Servico s) {
    this.historico.add(s);
  }

}
