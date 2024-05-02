package SGOficina;
import java.util.Set;

public class Veiculo {

  private int id;
  private Ficha ficha;

  public Veiculo(int id) {
    this.id = id;
    this.ficha = new Ficha();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void addHistorico(Servico s) {
    this.ficha.addHistorico(s);
    //this.setProximo(null);
  }
}
