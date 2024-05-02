package GUI;
import java.util.InputMismatchException;
import java.util.Scanner;

import SGOficina.SGOficina;

public class Menu {

  private SGOficina oficina;
  Scanner scanner;

  public Menu() {
    System.out.print(
      "A que posto pertence este terminal? \nPara cliente insira qualquer numero \n> "
    );

    this.scanner = new Scanner(System.in);

    int id_posto = scanner.nextInt();
    scanner.nextLine();
    this.oficina = new SGOficina(id_posto);
  }

  public void MenuLogin() {
    clearScreen();
    print("--LOGIN--\n");

    print("Introduza Username: ");
    String username = scanner.nextLine();

    print("Introduza Password: ");
    String password = scanner.nextLine();
    oficina.autenticar(username, password);
    if (oficina.utilizadorType() == 1) { // é um cliente
      menuClient();
    } else { // é um mecânico
      //verificar competencias para o posto associado ao terminal
      menuMec();
    }
  }

  public void pressEnter() {
    System.out.println("Press Enter to Continue");
  }

  public void clearScreen() {
    System.out.println("\033[H\033[2J");
  }

  public void print(String input) {
    System.out.print(input);
  }

  public String buildMenuMec() {
    StringBuilder menu1 = new StringBuilder();
    menu1.append("----  OFICINA MECÂNICA: GESTÃO DE PEDIDOS  ----\n");
    menu1.append("1. Consultar pedidos de serviços\n");
    menu1.append("2. Executar próximo serviço\n");
    menu1.append("3. Exit\n");
    menu1.append("Escolha a sua opção: ");

    return menu1.toString();
  }

  public String buildMenuClient() {
    StringBuilder menu1 = new StringBuilder();
    menu1.append("----  OFICINA MECÂNICA: MENU DO CLIENTE  ----\n");
    menu1.append("1. Requisitar um serviço\n");
    menu1.append("2. Terminar sessão\n");
    menu1.append("Escolha a sua opção: ");

    return menu1.toString();
  }

  public Boolean menuClient() {
    boolean terminado = false;

    while (!terminado) {
      String menu = buildMenuClient();
      clearScreen();
      print(menu);
      int opcao;
      try {
        opcao = scanner.nextInt();
      } catch (InputMismatchException e) {
        opcao = -1;
      }

      scanner.nextLine();

      switch (opcao) {
        case 1:
          clearScreen();
          print("--REQUISITAR SERVIÇO--\n");
          print("Introduza o serviço que pretende efetuar: ");
          String nome_serv = scanner.nextLine();

          print("Introduza o nº do seu veículo: ");
          int id_veiculo = scanner.nextInt();
          scanner.nextLine();

          oficina.requisitarServico(nome_serv, id_veiculo);
          pressEnter();
          scanner.nextLine();

          break;
        case 2:
          print("Terminar Aplicação!!\n");
          terminado = true;
          break;
        default:
          print("Escolha Inválida. Introduza opção novamente! ");
          scanner.nextLine();
      }
    }
    return terminado;
  }

  public Boolean menuMec() {
    boolean terminado = false;

    while (!terminado) {
      String menu = buildMenuMec();
      clearScreen();
      print(menu);
      int opcao;
      try {
        opcao = scanner.nextInt();
      } catch (InputMismatchException e) {
        opcao = -1;
      }

      scanner.nextLine();

      switch (opcao) {
        case 1:
          clearScreen();
          oficina.consultaServicos();
          pressEnter();
          scanner.nextLine();

          break;
        case 2:
          clearScreen();
          oficina.executarServico();
          pressEnter();
          scanner.nextLine();

          break;
        case 3:
          print("Terminar Aplicação!!\n");
          terminado = true;
          break;
        default:
          print("Escolha Inválida. Introduza opção novamente! ");
          scanner.nextLine();
      }
    }
    return terminado;
  }

  public static void main(String[] args) {
    Menu menu = new Menu();
    menu.MenuLogin();
  }
}
