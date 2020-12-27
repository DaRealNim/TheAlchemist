import java.util.Scanner;
import java.lang.reflect.Constructor;

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
          for (int i = 0; i < gameProg.unlockedLevels.length; i++) {
              if (gameProg.unlockedLevels[i])
                System.out.println("Level " + (i + 1));
          }
          while (true)
          {
            System.out.print(">>>");

            String levelChosenString = userMenuInput.nextLine();
            int levelChosen;

            if (isNumeric(levelChosenString))
              levelChosen = Integer.valueOf(levelChosenString);
            else {
              System.out.println("Please enter a number");
              break;
            }

            if (levelChoiceIsValid(levelChosen)) {
              System.out.println("Invalid level choice");
              break;
            }

            if (levelIsUnlocked(levelChosen))
            {
              try {
                Class<?> level = Class.forName("Level" + levelChosenString);
                Constructor<?> levelConstructor = level.getConstructor();
                Object levelInstance = levelConstructor.newInstance();
              }
              catch (Exception e)
              {
                System.out.println(e.getCause()); //Delete this! 4 debugging
                System.out.println("The level you're trying to play doesn't exist.");
              }
            }
            else
              System.out.println("This level is still locked!");
          }
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

  private boolean levelChoiceIsValid(int levelId) {
    return (levelId > gameProg.unlockedLevels.length || levelId < 1);
  }

  private boolean levelIsUnlocked(int levelId) {
    return (gameProg.unlockedLevels[levelId - 1]);
  }

  private boolean isNumeric(String str) {
        return (str != null && str.matches("[0-9.]+"));
  }


  public void outputGraphics() {
    System.out.println("graphics not implemented yet silly");
  }

  public void inputGraphics() {
    System.out.println("hereneither");
  }
}
