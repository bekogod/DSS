package DAO;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

// import Utilizador;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import SGOficina.Users.Cliente;
import SGOficina.Users.Mecanico;
import SGOficina.Users.Utilizador;

public class UtilizadorDAO implements Map<String, Utilizador> {

  private ServicoDAO servDAO;

  /**
   * NÃO IMPLEMENTADO!
   * @return
   * @return
   * @return ainda nada!
   */
  @Override
  public void clear() {
    throw new NullPointerException("Not implemented!");
  }

  private static UtilizadorDAO singleton = null;

  public UtilizadorDAO(ServicoDAO sDAO) {
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
        "CREATE TABLE IF NOT EXISTS utilizadores (" +
        "Name VARCHAR(45) NOT NULL PRIMARY KEY," +
        "Password VARCHAR(45) NOT NULL);";
      stm.executeUpdate(sql);

      String sql2 =
        "CREATE TABLE IF NOT EXISTS cliente (" +
        "Utilizador_Name VARCHAR(45) NOT NULL," +
        "NIF INT NOT NULL," +
        "Morada VARCHAR(255) NOT NULL," +
        "Email VARCHAR(100)," +
        "Telemovel INT," +
        "PRIMARY KEY (Utilizador_Name)," +
        "FOREIGN KEY (Utilizador_Name) REFERENCES utilizadores(Name));";
      stm.executeUpdate(sql2);
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
  // public static UtilizadorDAO getInstance() {
  //   if (singleton == null) {
  //     singleton = new UtilizadorDAO();
  //   }
  //   return singleton;
  // }

  /**
   * @return número de turmas na base de dados
   */
  @Override
  public int size() {
    throw new NullPointerException("Not implemented!");
    // int i = 0;
    // try (
    //   Connection conn = DriverManager.getConnection(
    //     DAOconfig.URL,
    //     DAOconfig.USERNAME,
    //     DAOconfig.PASSWORD
    //   );
    //   Statement stm = conn.createStatement();
    //   ResultSet rs = stm.executeQuery("SELECT count(*) FROM turmas")
    // ) {
    //   if (rs.next()) {
    //     i = rs.getInt(1);
    //   }
    // } catch (Exception e) {
    //   // Erro a criar tabela...
    //   e.printStackTrace();
    //   throw new NullPointerException(e.getMessage());
    // }
    // return i;
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
    // boolean r;
    // try (
    //   Connection conn = DriverManager.getConnection(
    //     DAOconfig.URL,
    //     DAOconfig.USERNAME,
    //     DAOconfig.PASSWORD
    //   );
    //   Statement stm = conn.createStatement();
    //   ResultSet rs = stm.executeQuery(
    //     "SELECT Id FROM turmas WHERE Id='" + key.toString() + "'"
    //   )
    // ) {
    //   r = rs.next();
    // } catch (SQLException e) {
    //   // Database error!
    //   e.printStackTrace();
    //   throw new NullPointerException(e.getMessage());
    // }
    // return r;
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
    Utilizador u = (Utilizador) value;
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
  public Utilizador get(Object key) {
    Utilizador u = null;

    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement();
      ResultSet rs = stm.executeQuery(
        "SELECT * FROM utilizadores WHERE Name='" + key + "'"
      )
    ) {
      if (rs.next()) { // A chave existe na tabela
        String name = rs.getString("Name");
        String password = rs.getString("Password");

        ResultSet rs2 = stm.executeQuery(
          "SELECT * FROM cliente WHERE Utilizador_Name='" + key + "'"
        );
        if (rs2.next()) { // É um cliente
          int nif = Integer.parseInt(rs2.getString("NIF"));
          String morada = rs2.getString("Morada");
          String email = rs2.getString("Email");
          int numeroTelefone = Integer.parseInt(rs2.getString("Telemovel"));

          u = new Cliente(name, password, nif, morada, email, numeroTelefone);
        } else {
          ResultSet rs3 = stm.executeQuery(
            "SELECT * FROM Competencias WHERE user_name='" + key + "'"
          );
          u = new Mecanico(name, password);

          while (rs3.next()) { // É um mecanico com competencias
            String servName = rs3.getString("servico_name");
            ((Mecanico) u).addCompetencia(servDAO.get(servName));
          }
        }
      } else {
        throw new NullPointerException();
        // return null;
      }
    } catch (SQLException e) {
      // Database error!
      System.err.println("DATABASE ERROR");
      e.printStackTrace();
      throw new NullPointerException(e.getMessage());
    }

    return u;
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
  public Set<Entry<String, Utilizador>> entrySet() {
    throw new NullPointerException("Not implemented!");
  }

  /**
   * Método que obtém a lista de alunos da turma
   *
   * @param tid o id da turma
   * @return a lista de alunos da turma
   */
  //   private Collection<String> getAlunosTurma(String tid, Statement stm)
  //     throws SQLException {
  //     Collection<String> alunos = new TreeSet<>();
  //     try (
  //       ResultSet rsa = stm.executeQuery(
  //         "SELECT Num FROM alunos WHERE Turma='" + tid + "'"
  //       )
  //     ) {
  //       while (rsa.next()) {
  //         alunos.add(rsa.getString("Num"));
  //       }
  //     } // execepção é enviada a quem chama o método - este try serve para fechar o ResultSet automaticamente
  //     return alunos;
  //   }

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
  public Utilizador put(String key, Utilizador u) {
    Utilizador res = null;
    try (
      Connection conn = DriverManager.getConnection(
        DAOconfig.URL,
        DAOconfig.USERNAME,
        DAOconfig.PASSWORD
      );
      Statement stm = conn.createStatement()
    ) {
      stm.executeUpdate(
        "INSERT INTO utilizadores " +
        "VALUES ('" +
        key +
        "', '" +
        u.getPassword() +
        "') ;"
      );
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
  public Utilizador remove(Object key) {
    throw new NullPointerException("Not implemented!");
    // Turma t = this.get(key);
    // try (
    //   Connection conn = DriverManager.getConnection(
    //     DAOconfig.URL,
    //     DAOconfig.USERNAME,
    //     DAOconfig.PASSWORD
    //   );
    //   Statement stm = conn.createStatement();
    //   PreparedStatement pstm = conn.prepareStatement(
    //     "UPDATE alunos SET Turma=? WHERE Num=?"
    //   )
    // ) {
    //   // retirar os alunos da turma
    //   pstm.setNull(1, Types.VARCHAR);
    //   for (String na : t.getAlunos()) {
    //     pstm.setString(2, na);
    //     pstm.executeUpdate();
    //   }
    //   // apagar a turma
    //   stm.executeUpdate("DELETE FROM turmas WHERE Id='" + key + "'");
    // } catch (Exception e) {
    //   // Database error!
    //   e.printStackTrace();
    //   throw new NullPointerException(e.getMessage());
    // }
    // return t;
  }

  /**
   * Adicionar um conjunto de turmas à base de dados
   *
   * @param turmas as turmas a adicionar
   * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
   */
  @Override
  public void putAll(Map<? extends String, ? extends Utilizador> utilizadores) {
    for (Utilizador u : utilizadores.values()) {
      this.put(u.getNome(), u);
    }
  }

  //   /**
  //    * Apagar todas as turmas
  //    *
  //    * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
  //    */
  //   @Override
  //   public void clear() {
  //     try (
  //       Connection conn = DriverManager.getConnection(
  //         DAOconfig.URL,
  //         DAOconfig.USERNAME,
  //         DAOconfig.PASSWORD
  //       );
  //       Statement stm = conn.createStatement()
  //     ) {
  //       stm.executeUpdate("UPDATE alunos SET Turma=NULL");
  //       stm.executeUpdate("TRUNCATE turmas");
  //     } catch (SQLException e) {
  //       // Database error!
  //       e.printStackTrace();
  //       throw new NullPointerException(e.getMessage());
  //     }
  //   }

  /**
   * @return Todos as turmas da base de dados
   */
  @Override
  public Collection<Utilizador> values() {
    throw new NullPointerException("Not implemented!");
    // Collection<Turma> res = new HashSet<>();
    // try (
    //   Connection conn = DriverManager.getConnection(
    //     DAOconfig.URL,
    //     DAOconfig.USERNAME,
    //     DAOconfig.PASSWORD
    //   );
    //   Statement stm = conn.createStatement();
    //   ResultSet rs = stm.executeQuery("SELECT Id FROM turmas")
    // ) { // ResultSet com os ids de todas as turmas
    //   while (rs.next()) {
    //     String idt = rs.getString("Id"); // Obtemos um id de turma do ResultSet
    //     Turma t = this.get(idt); // Utilizamos o get para construir as turmas uma a uma
    //     res.add(t); // Adiciona a turma ao resultado.
    //   }
    // } catch (Exception e) {
    //   // Database error!
    //   e.printStackTrace();
    //   throw new NullPointerException(e.getMessage());
    // }
    // return res;
  }
  /**
//    * NÃO IMPLEMENTADO!
//    * @return ainda nada!
//    */
  //   @Override
  //   public Set<Entry<String, Turma>> entrySet() {
  //     throw new NullPointerException(
  //       "public Set<Map.Entry<String,Aluno>> entrySet() not implemented!"
  //     );
  //   }

}
