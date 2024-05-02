package DAO;
public class DAOconfig {

  static final String USERNAME = "gusto";
  static final String PASSWORD = "12345";
  private static final String DATABASE = "SGOficina";
  private static final String DRIVER = "jdbc:mariadb"; // Usar para MariaDB
  //   private static final String DRIVER = "jdbc:mysql"; // Usar para MySQL

  static final String URL = DRIVER + "://localhost:3306/" + DATABASE;
}
