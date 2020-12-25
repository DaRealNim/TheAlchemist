import java.util.Scanner;

public class Menu implements InputOutput {

  private boolean gui;
  private Progression gameProg;
  private Inventory userInv;

  public Menu(boolean userChoseGui, Progression prog, Inventory inv) {
      gui = userChoseGui;
      gameProg = prog;
      userInv = inv;
  }

  public void displayMenu() {
    if (gui)
      outputGraphics();
    else {
        outputText();
        inputText();
    }
  }

  public void outputText() {
    System.out.println("        -. .");
    System.out.println("     ______',' ,");
    System.out.println("  ,'      ,'  ', ',");
    System.out.println(",'      ,'      |  |");
    System.out.println("\\       \\       |  |");
    System.out.println(" \\ /^\\   \\     ,' ,'");
    System.out.println("      \\    \\ ,' ,'");
    System.out.println("/ ~-.___\\.-'  ,'");
    System.out.println("/   .______.- \\");
    System.out.println("/  /'      \\   \\");
    System.out.println("\\./         \\/'\n");

    System.out.println("==================Welcome 2 ebic haxx0r simul4t0re !111 !!!============");
    System.out.println("====================Я хотел бы узнать тебя получше.====================");
  }

  public void inputText() {
    Scanner userMenuInput = new Scanner(System.in);

    while (true)
    {
      System.out.println("1. Serve Motherland (Choose level) \n2. Settings  \n3. Exit ");
      System.out.print("\n выберите\n >>>");
      String userOption = userMenuInput.nextLine();

      switch(userOption) {
        case "1":
          System.out.println("Placeholder");
          break;
        case "2":
          System.out.println("хахаха, ты правда думал, что в этой игре есть настройки?");
          break;
        case "3":
          System.exit(0);
          break;
        default:
          System.out.println("Unrecognized, please enter a number.");
          break;
      }
    }
  }

  public void outputGraphics() {
    System.out.println("graphics not implemented yet silly");
  }

  public void inputGraphics() {
    System.out.println("hereneither");
  }
}
