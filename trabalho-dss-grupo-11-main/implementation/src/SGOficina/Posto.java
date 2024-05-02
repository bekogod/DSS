package SGOficina;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**

CREATE TABLE IF NOT EXISTS Posto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    limiteServ INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Posto_Servico (
    posto_id INT,
    servico_name VARCHAR(255),
    PRIMARY KEY (posto_id, servico_name),
    FOREIGN KEY (posto_id) REFERENCES Posto(id),
    FOREIGN KEY (servico_name) REFERENCES Servico(Name)
);
  
 */

public class Posto {

  private int id;


  public Posto(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
