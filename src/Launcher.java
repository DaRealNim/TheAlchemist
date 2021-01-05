import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import java.util.Random;


public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                launcher(args);
            }
        });
    }

    private static void launcher(String[] args) {
        File saveFile = new File("SavedGame.dat");
        Inventory inv = new Inventory();
        Progression prog = new Progression();

        try {
            if (saveFile.createNewFile()) {
                FileOutputStream savedFileOutputStream = new FileOutputStream(saveFile);
                ObjectOutputStream savedObjectOutputStream = new ObjectOutputStream(savedFileOutputStream);

                savedObjectOutputStream.writeObject(inv);
                savedObjectOutputStream.writeObject(prog);

                savedObjectOutputStream.close();
            }
            else {
                FileInputStream savedFileInputStream = new FileInputStream(saveFile);
                ObjectInputStream savedObjectInputStream = new ObjectInputStream(savedFileInputStream);

                inv = (Inventory) savedObjectInputStream.readObject();
                prog = (Progression) savedObjectInputStream.readObject();

                savedObjectInputStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("Error loading save file, exiting...");
            System.exit(0);
        }

        Menu mainMenu;
        if (args.length == 0)
            mainMenu = instanciateMenuGUI(prog, inv);
        else if (args[0].equals("text"))
            mainMenu = instanciateMenuText(prog, inv);
        else
            mainMenu = instanciateMenuGUI(prog, inv);
        mainMenu.displayMenu();
    }

    private static Menu instanciateMenuGUI(Progression prog, Inventory inv) {
        FontManager.registerFont("fnt_planewalker", "./res/fonts/planewalker.ttf");

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
        AudioManager.registerSound("level3music", "./res/sounds/music/DarkMusic.wav");
        AudioManager.registerSound("level4music", "./res/sounds/music/InnerJourney.wav");

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

        return new Menu(true, prog, inv, new Window());
    }

    private static Menu instanciateMenuText(Progression prog, Inventory inv) {
        return new Menu(false, prog, inv, null);
    }

}
