package SGOficina;
import java.util.AbstractMap.SimpleEntry;

import DAO.PostoDAO;
import DAO.ServicoDAO;
import DAO.UtilizadorDAO;
import DAO.VeiculoDAO;
import SGOficina.Users.Cliente;
import SGOficina.Users.Utilizador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SGOficina {

  private Utilizador utilizadorAtual;
  private int idPosto;

  private UtilizadorDAO utiDAO;
  private VeiculoDAO veicDAO;

  private PostoDAO postoDAO;
  private ServicoDAO servDAO;

  public SGOficina(int idPosto) {
    this.idPosto = idPosto;
    postoDAO = new PostoDAO();
    servDAO = new ServicoDAO();
    veicDAO = new VeiculoDAO(servDAO);

    utiDAO = new UtilizadorDAO(servDAO);
  }

  public void autenticar(String nome, String password) {
    try {
      utilizadorAtual = utiDAO.get(nome);
    } catch (Exception e) {
      System.err.println("User não existe");
      return;
    }

    if (!(password.equals(utilizadorAtual.getPassword()))) {
      System.err.println("Password invalida");
    } else {
      System.out.println("User " + utilizadorAtual.toString() + " Autenticado");
    }

    return;
  }

  public void consultaServicos() {
    List<SimpleEntry<Integer, String>> prox_servs = postoDAO.consultaServ(
      this.idPosto
    );
    for (SimpleEntry<Integer, String> entry : prox_servs) {
      System.out.println(
        "Veículo: " + entry.getKey() + "; Serviço: " + entry.getValue() + "\n"
      );
    }
  }

  public boolean executarServico() {
    if (!postoDAO.isEmptyQueue(this.idPosto)) {
      Servico serv = postoDAO.getQueueServ(idPosto);
      Veiculo veic = postoDAO.getQueueVeic(idPosto);

      veicDAO.addHistorico(veic.getId(), serv);
      postoDAO.removeQueue(this.idPosto);
      System.out.println("O serviço executado!!!\n");
      return true;
    } else {
      System.err.println("Não há serviço a ser realizado!!!\n");
      return false;
    }
  }

  public int utilizadorType() {
    Utilizador u = this.utilizadorAtual;
    if (u instanceof Cliente) return 1;
    return 2; //Utilizador é um mecânico
  }

  public void requisitarServico(String nome_serv, int idVeiculo) {
    Set<Servico> serv_dsp = new HashSet<>();
    List<Posto> postos = this.postoDAO.getAll();
    Servico new_serv = null; //comeca a null

    List<String> serv_carro = veicDAO.getServicos(idVeiculo);
    Posto new_posto = null;

    for (Posto posto : postos) {
      serv_dsp = postoDAO.getServicosFromPosto(posto.getId());
      new_posto = posto;
      for (Servico s : serv_dsp) {
        if (s.getNome().equals(nome_serv)) {
          if (serv_carro.contains(nome_serv)) {
            new_serv = new Servico(nome_serv);

            break;
          } else new_serv = null;
        }
      }
      if (new_serv != null) break; // ja encontrou um posto
    }
    if (new_serv == null) {
      System.err.println("Não foi possível requesitar o serviço!!!\n");
      return;
    }
    Veiculo carro = veicDAO.get(idVeiculo);

    try {
      postoDAO.putQueue(new_posto.getId(), carro.getId(), new_serv.getNome());
    } catch (Exception e) {
      // TODO: handle exception
    }

    System.out.println("O serviço foi requisitado com sucesso!!!\n");
    // isto ja é no veiculo ou cliente, not sure
  }
  // public static void main(String[] args) {
  //   SGOficina sgo = new SGOficina(3);
  //   sgo.autenticar("gusto", "campos");
  //   // sgo.requisitarServico("O alinhamento da direção", 1);
  //   sgo.executarServico();
  // }
}
