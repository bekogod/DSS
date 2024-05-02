package DAO;
import java.sql.*;
import java.util.AbstractMap.SimpleEntry;

import SGOficina.Posto;
import SGOficina.Servico;
import SGOficina.Veiculo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostoDAO implements Map<Integer, Posto> {

  /**
   * NÃO IMPLEMENTADO!
   * @return ainda nada!
   */
  @Override
  public void clear() {
    throw new NullPointerException("Not implemented!");
  }

  private static PostoDAO singleton = null;

  public PostoDAO() {
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      String sql =
        "CREATE TABLE IF NOT EXISTS Posto (" +
        "id INT PRIMARY KEY AUTO_INCREMENT," +
        "limiteServ INT NOT NULL" +
        ");";
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      // Erro a criar tabela...
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
  }

  /**
   * Implementação do padrão Singleton
   *
   * @return devolve a instância única desta classe
   */
  public static PostoDAO getInstance() {
    if (singleton == null) {
      singleton = new PostoDAO();
    }
    return singleton;
  }

  /**
   * @return número de turmas na base de dados
   */
  @Override
  public int size() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Método que verifica se existem turmas
   *
   * @return true se existirem 0 turmas
   */
  @Override
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Método que cerifica se um id de turma existe na base de dados
   *
   * @param key id da turma
   * @return true se a turma existe
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public boolean containsKey(Object key) {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Verifica se uma turma existe na base de dados
   *
   * Esta implementação é provisória. Devia testar o objecto e não apenas a chave.
   *
   * @param value ...
   * @return ...
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public boolean containsValue(Object value) {
    Posto p = (Posto) value;
    return this.containsKey(p.getId());
  }

  /**
   * Obter uma turma, dado o seu id
   *
   * @param key id da turma
   * @return a turma caso exista (null noutro caso)
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Posto get(Object key) {
    Posto p = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM Posto WHERE id ='" + key + "'"
      )
    ) {
      if (rs.next()) { // A chave existe na tabela
        p = new Posto(rs.getInt("id"));
      } // É um cliente
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return p;
  }

  public List<Posto> getAll() {
    List<Posto> lp = new ArrayList<Posto>();
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Posto;")
    ) {
      while (rs.next()) { // A chave existe na tabela
        lp.add(new Posto(rs.getInt("id")));
      }
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return lp;
  }

  /**
   * NÃO IMPLEMENTADO!
   * @return ainda nada!
   */
  @Override
  public Set<Integer> keySet() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * NÃO IMPLEMENTADO!
   * @return
   * @return ainda nada!
   */
  @Override
  public Set<Entry<Integer, Posto>> entrySet() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Insere uma turma na base de dados
   *
   * ATENÇÂO: Esta implementação é provisória.
   * Falta devolver o valor existente (caso exista um)
   * Falta remover a sala anterior, caso esteja a ser alterada
   * Deveria utilizar transacções...
   *
   * @param key o id da turma
   * @param t a turma
   * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Posto put(Integer key, Posto u) {
    Posto res = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeUpdate("INSERT INTO Posto " + "VALUES ('" + key + "') ;");
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
    return res;
  }

  /**
   * Remover uma turma, dado o seu id
   *
   * NOTA: Não estamos a apagar a sala...
   *
   * @param key id da turma a remover
   * @return a turma removida
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Posto remove(Object key) {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Adicionar um conjunto de turmas à base de dados
   *
   * @param turmas as turmas a adicionar
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public void putAll(Map<? extends Integer, ? extends Posto> Posto) {
    for (Posto p : Posto.values()) {
      this.put(p.getId(), p);
    }
  }

  /**
   * @return Todos as turmas da base de dados
   */
  @Override
  public Collection<Posto> values() {
    throw new NullPointerException("Not implemented!");
  }

  public Set<Servico> getServicosFromPosto(int id) {
    Set<Servico> lp = new HashSet<>();
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "select * from Posto_Servico where Posto_id = " + id + ";"
      )
    ) {
      while (rs.next()) { // A chave existe na tabela
        lp.add(new Servico(rs.getString("servico_name")));
      }
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return lp;
  }

  public void putQueue(int idPosto, int idVeiculo, String nome)
    throws SQLException {
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeUpdate(
        "INSERT INTO queue (posto_id, veiculo_id, proximoServico)" +
        "VALUES (" +
        idPosto +
        ", " +
        idVeiculo +
        ", '" +
        nome +
        "' );"
      );
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw e;
    }
  }

  public boolean isEmptyQueue(int idPosto) {
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM queue WHERE posto_id = " + idPosto + ";"
      );

      return (!rs.next());
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
  }

  public Servico getQueueServ(int idPosto) {
    Servico s = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM queue WHERE posto_id = " + idPosto + ";"
      );
      if (rs.next()) {
        s = new Servico(rs.getString("proximoServico"));
      }
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
    return s;
  }

  public Veiculo getQueueVeic(int idPosto) {
    Veiculo v = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM queue WHERE posto_id = " + idPosto + ";"
      );
      if (rs.next()) {
        v = new Veiculo(rs.getInt("veiculo_id"));
      }
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
    return v;
  }

  public List<SimpleEntry<Integer, String>> consultaServ(int idPosto) {
    List<SimpleEntry<Integer, String>> result = new ArrayList<>();
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM queue WHERE posto_id='" + idPosto + "'"
      )
    ) {
      while (rs.next()) {
        result.add(
          new SimpleEntry<Integer, String>(
            rs.getInt("veiculo_id"),
            rs.getString("proximoServico")
          )
        );
      }
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return result;
  }

  public void removeQueue(int idPosto) {
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeQuery(
        "DELETE FROM queue WHERE posto_id = " + idPosto + " LIMIT 1;"
      );
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }
  }
}
