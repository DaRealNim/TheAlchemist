import java.util.Scanner;
import java.lang.reflect.Constructor;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Menu implements InputOutput {

  private boolean gui;
  private Progression gameProg;
  private Inventory userInv;
  private Window mainWindow;

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
          for (int i = 1; i < gameProg.unlockedLevels.length; i++) {
              if (levelIsUnlocked(i))
                System.out.println("Level " + i);
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
              instanciateLevel(levelChosen);
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



  public void outputGraphics() {
    mainWindow = new Window();

    JPanel optionPanel = new JPanel();

    JMenuBar buttons = new JMenuBar();

    JButton chooseLevelButton = new JButton("Serve Motherland");
    JButton quitGameButton = new JButton("Exit game");

    chooseLevelButton.addActionListener((ActionEvent e) -> { chooseLevel(); });
    quitGameButton.addActionListener((ActionEvent e) -> { quitGame(); });

    buttons.add(chooseLevelButton);
    buttons.add(quitGameButton);

    optionPanel.add(buttons);
    // mainWindow.getContentPane().add(optionPanel);
    mainWindow.setJMenuBar(buttons);
    // mainWindow.setContentPane(optionPanel);
    mainWindow.repaint();
  }

  public void quitGame() {
    System.exit(0);
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

  public void chooseLevel() {
    JPanel levelPanel = new JPanel();

    JMenuBar buttons = new JMenuBar();

    for (int i = 0; i < gameProg.unlockedLevels.length; i++) {
      JButton levelButton = new JButton("Level " + (i + 1));

      final int levelId = i+1;

      levelButton.addActionListener((ActionEvent e) -> {    if (levelIsUnlocked(levelId)) {
                                                                Thread thread = new Thread() {
                                                                    public void run() {
                                                                        instanciateLevel(levelId);
                                                                    }
                                                                };
                                                                thread.start();
                                                            } else {
                                                                System.out.println("Level is locked!");
                                                            }
                                                       });

      buttons.add(levelButton);
      System.out.print("button created!");
    }

    mainWindow.getContentPane().removeAll();
    levelPanel.add(buttons);
    // mainWindow.setContentPane(levelPanel);
    mainWindow.setJMenuBar(buttons);
    mainWindow.revalidate();
    mainWindow.repaint();
  }

  public void inputGraphics() {
    System.out.println("hereneither");
  }

  public void instanciateLevel(int levelId)
  {
    try {
      Class<?> level = Class.forName("Level" + (levelId));
      Constructor<?> levelConstructor;
      Object levelInstance;
      if (gui) {
        levelConstructor = level.getConstructor(new Class[] {Window.class});
        levelInstance = levelConstructor.newInstance(mainWindow);
      } else {
        levelConstructor = level.getConstructor();
        levelInstance = levelConstructor.newInstance();
      }
    }
    catch (Exception e) {
      System.out.println(e); //Delete this! 4 debugging
      System.out.println("The level you're trying to play doesn't exist.");
    }
  }
}
