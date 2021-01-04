import java.util.Scanner;
import java.lang.reflect.Constructor;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Menu implements InputOutput {

    private boolean gui;
    private Progression gameProg;
    private Inventory userInv;
    private Window mainWindow;
    private String menuMusicPath;
    private File menuMusicFile;
    private AudioInputStream audioInputStream;
    private Clip mainMenuMusic;

    public Menu(boolean userChoseGui, Progression prog, Inventory inv, Window w) {
        gui = userChoseGui;
        gameProg = prog;
        userInv = inv;
        mainWindow = w;
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
                    while (true) {
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

        Random rd = new Random();
        if(mainMenuMusic == null) {
            try {
                switch(rd.nextInt(3)) {
                    case 0:
                        menuMusicPath = "./res/sounds/music/HouseOfEvil (Menu).wav";
                        menuMusicFile = new File(menuMusicPath);
                        audioInputStream = AudioSystem.getAudioInputStream(menuMusicFile.toURI().toURL());

                        mainMenuMusic = AudioSystem.getClip();
                        mainMenuMusic.open(audioInputStream);
                        mainMenuMusic.loop(Clip.LOOP_CONTINUOUSLY);
                        mainMenuMusic.start();
                    break;
                    case 1:
                        menuMusicPath = "./res/sounds/music/BeforeDawn (Menu).wav";
                        menuMusicFile = new File(menuMusicPath);

                        audioInputStream = AudioSystem.getAudioInputStream(menuMusicFile.toURI().toURL());
                        mainMenuMusic = AudioSystem.getClip();
                        mainMenuMusic.open(audioInputStream);
                        mainMenuMusic.loop(Clip.LOOP_CONTINUOUSLY);
                        mainMenuMusic.start();
                    break;
                    case 2:
                        menuMusicPath = "./res/sounds/music/AdagioInC (Menu).wav";
                        menuMusicFile = new File(menuMusicPath);
                        audioInputStream = AudioSystem.getAudioInputStream(menuMusicFile.toURI().toURL());

                        mainMenuMusic = AudioSystem.getClip();
                        mainMenuMusic.open(audioInputStream);
                        mainMenuMusic.loop(Clip.LOOP_CONTINUOUSLY);
                        mainMenuMusic.start();
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!mainMenuMusic.isRunning()) {
            mainMenuMusic.start();
        }

        mainWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        mainWindow.getContentPane().removeAll();

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./res/images/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setSize(600,600);
        picLabel.setPreferredSize(new Dimension(600,600));
        picLabel.setLocation(100,0);
        mainWindow.add(picLabel);

        CustomButton chooseLevelButton = new CustomButton("Play", "./res/images/button.png", 400, 100, () -> {
            chooseLevel();
        });
        CustomButton quitGameButton = new CustomButton("Exit game", "./res/images/button.png", 400, 100, () -> {
            quitGame();
        });

        chooseLevelButton.setLocation(200,600);
        chooseLevelButton.setFontSize(40);
        quitGameButton.setLocation(200,750);
        quitGameButton.setFontSize(40);

        mainWindow.getContentPane().add(chooseLevelButton);
        mainWindow.getContentPane().add(quitGameButton);

        mainWindow.paintBackground("./res/images/bg_menu.png");
        mainWindow.revalidate();
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
        mainWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        mainWindow.getContentPane().removeAll();

        for (int i = 0; i < gameProg.unlockedLevels.length; i++) {
            final int levelId = i+1;
            CustomButton levelButton = new CustomButton("Level " + (i + 1), "./res/images/button.png", 200, 50, () -> {
                if (levelIsUnlocked(levelId)) {
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
            levelButton.setLocation(300, 400+(i*70));
            mainWindow.getContentPane().add(levelButton);
        }

        CustomButton levelButton = new CustomButton("Back", "./res/images/button.png", 200, 50, () -> {
            displayMenu();
        });
        levelButton.setLocation(300, 450+(gameProg.unlockedLevels.length*70));
        mainWindow.getContentPane().add(levelButton);

        mainWindow.paintBackground("./res/images/bg_menu.png");

        mainWindow.revalidate();
        mainWindow.repaint();

        if(!mainMenuMusic.isRunning()) {
            mainMenuMusic.start();
        }
    }

    public void inputGraphics() {
        System.out.println("hereneither");
    }

    public void instanciateLevel(int levelId) {
        mainMenuMusic.close();
        try {
            Class<?> level = Class.forName("Level" + (levelId));
            Constructor<?> levelConstructor;
            Object levelInstance;
            if (gui) {
                levelConstructor = level.getConstructor(new Class[] {Window.class, Menu.class, Progression.class, Inventory.class, Integer.class});
                levelInstance = levelConstructor.newInstance(mainWindow, this, gameProg, userInv, levelId);
            } else {
                levelConstructor = level.getConstructor(new Class[] {Progression.class, Inventory.class});
                levelInstance = levelConstructor.newInstance(gameProg, userInv);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("The level you're trying to play doesn't exist.");
        }
    }

    public Clip getMusic() {
        return mainMenuMusic;
    }
}
