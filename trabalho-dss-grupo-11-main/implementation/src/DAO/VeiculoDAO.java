package DAO;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import SGOficina.Servico;
import SGOficina.Veiculo;

public class VeiculoDAO implements Map<Integer, Veiculo> {

  private ServicoDAO servDAO;

  /**
   * NÃO IMPLEMENTADO!
   * @return ainda nada!
   */
  @Override
  public void clear() {
    throw new NullPointerException("Not implemented!");
  }

  private static VeiculoDAO singleton = null;

  public VeiculoDAO(ServicoDAO sDAO) {
    servDAO = sDAO;

    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      String sql =
        "CREATE TABLE IF NOT EXISTS Veiculo (" +
        "email varchar(45) NOT NULL PRIMARY KEY" +
        ");";

      String sql2 =
        "CREATE TABLE IF NOT EXISTS Veiculo_TipoMotor (" +
        "veiculo_id INT," +
        "tipoMotor VARCHAR(255)," +
        "PRIMARY KEY (veiculo_id, tipoMotor)," +
        "FOREIGN KEY (veiculo_id) REFERENCES Veiculo(id) ON DELETE CASCADE," +
        "FOREIGN KEY (tipoMotor) REFERENCES TipoMotor(Name) ON DELETE CASCADE " +
        ");";
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      // Erro a criar tabela...
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
  }

  public List<String> getServicos(int id) {
    List<String> result = new ArrayList<>();
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM Veiculo_TipoMotor WHERE veiculo_id='" + id + "'"
      )
    ) {
      Set<String> motores = new HashSet<>();

      while (rs.next()) {
        motores.add(rs.getString("tipoMotor"));
      }
      for (String motor : motores) {
        result.addAll(servDAO.getServicosWithMotor(motor));
      }
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return result;
  }

  // public static VeiculoDAO getInstance() {
  //     if (singleton == null) {
  //         singleton = new VeiculoDAO();
  //     }
  //     return singleton;
  // }

  /**
   * @return número de veiculos na base de dados
   */
  @Override
  public int size() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Método que verifica se existem veiculos
   *
   * @return true se existirem 0 veiculos
   */
  @Override
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Método que cerifica se um email de veículo existe na base de dados
   *
   * @param key email do veículo
   * @return true se o veículo existe
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public boolean containsKey(Object key) {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Verifica se um veículo existe na base de dados
   *
   * Esta implementação é provisória. Devia testar o objeto e não apenas a chave.
   *
   * @param value ...
   * @return ...
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public boolean containsValue(Object value) {
    Veiculo v = (Veiculo) value;
    return this.containsKey(v.getId());
  }

  /**
   * Obter um veículo, dado o seu email
   *
   * @param key email do veículo
   * @return o veículo caso exista (null noutro caso)
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Veiculo get(Object key) {
    Veiculo v = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM Veiculo WHERE id='" + key + "'"
      )
    ) {
      if (rs.next()) { // O email existe na tabela
        // You may need to retrieve additional data from the database
        v = new Veiculo((int) key);
        ResultSet rs2 = stm.executeQuery(
          "SELECT * FROM fichaVeic WHERE veiculo_id='" + key + "'"
        );

        while (rs2.next()) {
          String servName = rs2.getString("servico_name");
          Servico servico = servDAO.get(servName);
          System.out.println(servico);
          v.addHistorico(servico);
        }
      }
    } catch (SQLException e) {
      System.out.println("DATABASE ERROR");
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return v;
  }

  /**
   * NÃO IMPLEMENTADO!
   *
   * @return ainda nada!
   */
  @Override
  public Set<Integer> keySet() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * NÃO IMPLEMENTADO!
   *
   * @return
   * @return ainda nada!
   */
  @Override
  public Set<Entry<Integer, Veiculo>> entrySet() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Insere um veículo na base de dados
   *
   * ATENÇÂO: Esta implementação é provisória.
   * Falta devolver o valor existente (caso exista um)
   * Deveria utilizar transacções...
   *
   * @param key o email do veículo
   * @param v   o veículo
   * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Veiculo put(Integer key, Veiculo v) {
    Veiculo res = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeUpdate("INSERT INTO Veiculo " + "VALUES ('" + key + "') ;");
    } catch (SQLException e) {
      System.out.println(" Database error!");
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
    return res;
  }

  /**
   * Remover um veículo, dado o seu email
   *
   * @param key email do veículo a remover
   * @return o veículo removido
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Veiculo remove(Object key) {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Adicionar um conjunto de veículos à base de dados
   *
   * @param veiculos os veículos a adicionar
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public void putAll(Map<? extends Integer, ? extends Veiculo> veiculos) {
    for (Veiculo v : veiculos.values()) {
      this.put(v.getId(), v);
    }
  }

  /**
   * @return Todos os veículos da base de dados
   */
  @Override
  public Collection<Veiculo> values() {
    throw new NullPointerException("Not implemented!");
  }

  public void addHistorico(int idCarro, Servico serv) {
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeUpdate(
        "INSERT INTO fichaVeic (veiculo_id, servico_name)" +
        "VALUES (" +
        idCarro +
        ",'" +
        serv.getNome() +
        "') ;"
      );
    } catch (SQLException e) {
      System.out.println(" Database error!");
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
  }
}
