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

public class ServicoDAO implements Map<String, Servico> {

  /**
   * NÃO IMPLEMENTADO!
   * @return ainda nada!
   */
  @Override
  public void clear() {
    throw new NullPointerException("Not implemented!");
  }

  private static ServicoDAO singleton = null;

  public ServicoDAO() {
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      String sql =
        "CREATE TABLE IF NOT EXISTS Servico ( " +
        " Name VARCHAR(255) NOT NULL PRIMARY KEY" +
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
  public static ServicoDAO getInstance() {
    if (singleton == null) {
      singleton = new ServicoDAO();
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
    Servico u = (Servico) value;
    return this.containsKey(u.getNome());
  }

  /**
   * Obter uma turma, dado o seu id
   *
   * @param key id da turma
   * @return a turma caso exista (null noutro caso)
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public Servico get(Object key) {
    Servico s = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM Servico WHERE Name='" + key + "'"
      )
    ) {
      if (rs.next()) { // A chave existe na tabela
        String name = rs.getString("Name");
        s = new Servico(name);
      } // É um cliente
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return s;
  }

  /**
   * NÃO IMPLEMENTADO!
   * @return ainda nada!
   */
  @Override
  public Set<String> keySet() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * NÃO IMPLEMENTADO!
   * @return
   * @return ainda nada!
   */
  @Override
  public Set<Entry<String, Servico>> entrySet() {
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
  public Servico put(String key, Servico u) {
    Servico res = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeUpdate("INSERT INTO servico " + "VALUES ('" + key + "') ;");
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
  public Servico remove(Object key) {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Adicionar um conjunto de turmas à base de dados
   *
   * @param turmas as turmas a adicionar
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public void putAll(Map<? extends String, ? extends Servico> servico) {
    for (Servico u : servico.values()) {
      this.put(u.getNome(), u);
    }
  }

  /**
   * @return Todos as turmas da base de dados
   */
  @Override
  public Collection<Servico> values() {
    throw new NullPointerException("Not implemented!");
  }

  public static void main(String[] args) {
    ServicoDAO uDAO = new ServicoDAO();
    System.out.println(uDAO.get("gusto"));
  }

  public List<String> getServicosWithMotor(String motor) {
    List<String> ls = new ArrayList<>();
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM Servico_TipoMotor WHERE TipoMotor_Name='" + motor + "'"
      )
    ) {
      while (rs.next()) { // A chave existe na tabela
        String name = rs.getString("Servico_Name");
        ls.add(name);
      } // É um cliente
    } catch (SQLException e) {
      // Database error!
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return ls;
  }
}
