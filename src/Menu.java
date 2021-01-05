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


public class Menu {

    private boolean gui;
    private Progression gameProg;
    private Inventory userInv;
    private Window mainWindow;

    public Menu(boolean userChoseGui, Progression prog, Inventory inv, Window w) {
        gui = userChoseGui;
        gameProg = prog;
        userInv = inv;
        mainWindow = w;

        if (gui) {
            SpriteManager.registerSprite("spr_arrow", "./res/images/arrow.png");
            SpriteManager.registerSprite("bg_level1", "./res/images/bg_level1.png");
            SpriteManager.registerSprite("bg_level2", "./res/images/bg_level2.png");
            SpriteManager.registerSprite("bg_level3", "./res/images/bg_level3.png");
            SpriteManager.registerSprite("bg_level4", "./res/images/bg_level4.png");
            SpriteManager.registerSprite("bg_menu", "./res/images/bg_menu.png");
            SpriteManager.registerSprite("spr_air", "./res/images/block_air_jelly.png");
            SpriteManager.registerSprite("spr_demon", "./res/images/block_demon_jelly.png");
            SpriteManager.registerSprite("spr_fire", "./res/images/block_fire_jelly.png");
            SpriteManager.registerSprite("spr_forest", "./res/images/block_forest_jelly.png");
            SpriteManager.registerSprite("spr_spirit", "./res/images/block_spirit_jelly.png");
            SpriteManager.registerSprite("spr_water", "./res/images/block_water_jelly.png");
            SpriteManager.registerSprite("spr_button", "./res/images/button.png");
            SpriteManager.registerSprite("spr_logo", "./res/images/logo.png");
            SpriteManager.registerSprite("spr_popup", "./res/images/popup.png");
            SpriteManager.registerSprite("spr_potion_blue", "./res/images/potion_blue.png");
            SpriteManager.registerSprite("spr_potion_green", "./res/images/potion_green.png");
            SpriteManager.registerSprite("spr_potion_red", "./res/images/potion_red.png");
            SpriteManager.registerSprite("spr_padlock", "./res/images/padlock.png");

            AudioManager.registerSound("click", "./res/sounds/click.wav");
            AudioManager.registerSound("fire", "./res/sounds/fire.wav");
            AudioManager.registerSound("plant", "./res/sounds/plant.wav");
            AudioManager.registerSound("rock", "./res/sounds/rock.wav");
            AudioManager.registerSound("water", "./res/sounds/waterbubble.wav");
            AudioManager.registerSound("air", "./res/sounds/windgust.wav");
            AudioManager.registerSound("win", "./res/sounds/win.wav");
            AudioManager.registerSound("lose", "./res/sounds/lose.wav");
            AudioManager.registerSound("potion", "./res/sounds/rocketsound.wav");
            AudioManager.registerSound("level1music", "./res/sounds/music/DestinationUnknown.wav");
            AudioManager.registerSound("level2music", "./res/sounds/music/EgyptianCrawl.wav");

            Random rd = new Random();
            switch(rd.nextInt(3)) {
                case 0:
                    AudioManager.registerSound("menumusic", "./res/sounds/music/HouseOfEvil (Menu).wav");
                break;
                case 1:
                    AudioManager.registerSound("menumusic", "./res/sounds/music/BeforeDawn (Menu).wav");
                break;
                case 2:
                    AudioManager.registerSound("menumusic", "./res/sounds/music/AdagioInC (Menu).wav");
                break;
            }
        }
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

        AudioManager.playSound("menumusic", true);

        mainWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        mainWindow.getContentPane().removeAll();

        JLabel picLabel = new JLabel(new ImageIcon(SpriteManager.getSprite("spr_logo")));
        picLabel.setSize(600,600);
        picLabel.setPreferredSize(new Dimension(600,600));
        picLabel.setLocation(100,0);
        mainWindow.add(picLabel);

        CustomButton chooseLevelButton = new CustomButton("Play", SpriteManager.getSprite("spr_button"), 400, 100, () -> {
            chooseLevel();
        });
        CustomButton quitGameButton = new CustomButton("Exit game", SpriteManager.getSprite("spr_button"), 400, 100, () -> {
            quitGame();
        });

        chooseLevelButton.setLocation(200,600);
        chooseLevelButton.setFontSize(40);
        quitGameButton.setLocation(200,750);
        quitGameButton.setFontSize(40);

        mainWindow.getContentPane().add(chooseLevelButton);
        mainWindow.getContentPane().add(quitGameButton);

        mainWindow.paintBackground("bg_menu");
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

        AudioManager.playSound("menumusic", true);

        mainWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        mainWindow.getContentPane().removeAll();

        for (int i = 0; i < gameProg.unlockedLevels.length; i++) {
            final int levelId = i+1;
            CustomButton levelButton = new CustomButton("Level " + (i + 1), SpriteManager.getSprite("spr_button"), 200, 50, () -> {
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
            if(!levelIsUnlocked(levelId)) {
                JLabel padlock = new JLabel(new ImageIcon(SpriteManager.getSprite("spr_padlock")));
                padlock.setSize(100, 100);
                padlock.setLocation(350, 375+(i*70));
                mainWindow.add(padlock);
            }
            levelButton.setLocation(300, 400+(i*70));
            mainWindow.getContentPane().add(levelButton);
        }

        CustomButton levelButton = new CustomButton("Back", SpriteManager.getSprite("spr_button"), 200, 50, () -> {
            displayMenu();
        });
        levelButton.setLocation(300, 450+(gameProg.unlockedLevels.length*70));
        mainWindow.getContentPane().add(levelButton);

        mainWindow.paintBackground("bg_menu");

        mainWindow.revalidate();
        mainWindow.repaint();
    }

    public void inputGraphics() {
        System.out.println("hereneither");
    }

    public void instanciateLevel(int levelId) {
        AudioManager.stopSound("menumusic");

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
}
