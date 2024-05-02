package SGOficina;
import java.util.Set;

/*

CREATE TABLE IF NOT EXISTS Servico (
    Name VARCHAR(255) NOT NULL PRIMARY KEY,
    
);

CREATE TABLE IF NOT EXISTS TipoMotor (
    Name VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Servico_TipoMotor (
    Servico_Name VARCHAR(255),
    TipoMotor_Name VARCHAR(255),
    PRIMARY KEY (Servico_Name, TipoMotor_Name),
    FOREIGN KEY (Servico_Name) REFERENCES Servico(Name),
    FOREIGN KEY (TipoMotor_Name) REFERENCES TipoMotor(Name)
);


> select * from Servico_TipoMotor;

+-------------------+----------------+
| Servico_Name      | TipoMotor_Name |
+-------------------+----------------+
| Troca de Travões  | ELETRICO       |
| Troca de Travões  | GASOLEO        |
| Troca de Travões  | GASOLINA       |
+-------------------+----------------+

*/

public class Servico {

  private String nome;

  // private Set<TipoMotor> motoresAssociados;

  public Servico(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
}
