import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.PrintWriter;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public abstract class Level {
    protected int id;
    protected int packetGoal;
    protected int scoreGoal;
    protected int deliveredPackets;
    protected int score;
    protected int scrollFirstLine;
    protected int scrollLastLine;
    protected int remainingLines;
    protected boolean hasScroll;
    protected boolean isUsingRedPotion;
    protected boolean isUsingBluePotion;
    protected boolean isUsingGreenPotion;
    protected Grid grid;
    protected Window window;
    protected Progression prog;
    protected Inventory inv;
    protected Menu menu;

    public Level(Window window, Menu menu, Progression prog, Inventory inv, Integer id) {
        grid = new Grid(getGridStrings());
        deliveredPackets = 0;
        score = 0;
        isUsingRedPotion = false;
        isUsingBluePotion = false;
        isUsingGreenPotion = false;
        hasScroll = (grid.getHeight() > 10);
        if (hasScroll) {
            scrollFirstLine = 0;
            scrollLastLine = 9;
            remainingLines = grid.getHeight() - scrollLastLine - 2;
        }
        window.setJMenuBar(null);
        window.revalidate();
        window.repaint();
        this.id = id;
        this.window = window;
        this.prog = prog;
        this.inv = inv;
        this.menu = menu;
        outputGraphics();
    }

    public Level(Progression prog, Inventory inv) {
        grid = new Grid(getGridStrings());
        deliveredPackets = 0;
        score = 0;
        hasScroll = (grid.getHeight() > 10);
        scrollFirstLine = 0;
        if (hasScroll) {
            scrollLastLine = 9;
            remainingLines = grid.getHeight() - scrollLastLine - 2;
        } else {
            scrollLastLine = grid.getHeight() - 2;
        }
        this.prog = prog;
        this.inv = inv;
    }

    public abstract String[] getGridStrings();

    public abstract void changeLevel();

    public abstract void unlockLevel();

    public abstract String getBackGroundIdentifier();

    public abstract String getMusicIdentifier();

    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void outputGraphics() {
        if (hasScroll)
            window.paintGrid(grid, scrollFirstLine, scrollLastLine);
        else
            window.paintGrid(grid, 0, grid.getHeight()-2);
        window.paintScore(score, scoreGoal);
        window.paintPackets(deliveredPackets, packetGoal);

        CustomButton quitButton = new CustomButton("Back to menu", SpriteManager.getSprite("spr_button"), 200, 45, () -> {
            AudioManager.stopSound(getMusicIdentifier());
            menu.chooseLevel();
            window.repaint();
        });
        quitButton.setLocation(600, 10);
        window.add(quitButton);

        CustomButton redpotionButton = new CustomButton("", SpriteManager.getSprite("spr_potion_red"), 46, 75, () -> {
            if (inv.redPotions > 0) {
                isUsingRedPotion = !isUsingRedPotion;
                isUsingBluePotion = false;
                isUsingGreenPotion = false;
                if (isUsingRedPotion)
                    window.updateMouseIcon("redpotion");
                else
                    window.updateMouseIcon("");
            }
        });
        redpotionButton.setLocation(400, 75);
        window.add(redpotionButton);

        CustomButton bluePotionButton = new CustomButton("", SpriteManager.getSprite("spr_potion_blue"), 46, 75, () -> {
            if (inv.bluePotions > 0) {
                isUsingBluePotion = !isUsingBluePotion;
                isUsingRedPotion = false;
                isUsingGreenPotion = false;
                if (isUsingBluePotion)
                    window.updateMouseIcon("bluepotion");
                else
                    window.updateMouseIcon("");
            }
        });
        bluePotionButton.setLocation(520, 75);
        window.add(bluePotionButton);

        CustomButton greenPotionButton = new CustomButton("", SpriteManager.getSprite("spr_potion_green"), 46, 75, () -> {
            if (inv.greenPotions > 0) {
                isUsingGreenPotion = !isUsingGreenPotion;
                isUsingRedPotion = false;
                isUsingBluePotion = false;
                if (isUsingGreenPotion)
                    window.updateMouseIcon("greenpotion");
                else
                    window.updateMouseIcon("");
            }
        });
        greenPotionButton.setLocation(640, 75);
        window.add(greenPotionButton);

        if(hasScroll) {
            window.paintArrow(remainingLines);
        }

        window.paintItems(inv.redPotions, inv.bluePotions, inv.greenPotions);
        window.paintBackground(getBackGroundIdentifier());
        window.revalidate();
        window.repaint();
    }

    public void outputText() {
        clearTerminal();
        if (hasScroll)
            grid.display(scrollFirstLine, scrollLastLine);
        else
            grid.display();
        System.out.println();
        if (hasScroll && remainingLines != 0) {
            System.out.println("| "+remainingLines);
            System.out.println("v");
            System.out.println();
        }
        System.out.println("Packets delivered: "+deliveredPackets);
        System.out.println("Score            : "+score);
        System.out.println("Potions rouge    : "+inv.redPotions);
        System.out.println("Potions bleues   : "+inv.bluePotions);
        System.out.println("Potions vertes   : "+inv.greenPotions);
        System.out.println("\n");
    }

    public void inputGraphics() {
        int x;
        int y;
        do {
            grid.blockClicked = false;
            while(!grid.blockClicked) {
                window.repaintMouseIcon();
            }
            delay();
            x = grid.blockClickedX;
            y = grid.blockClickedY;
        } while (grid.getBlock(x, y) == null);
        if (isUsingRedPotion) {
            score += grid.destroyColumn(x, hasScroll ? scrollFirstLine : 0, hasScroll ? scrollLastLine : grid.getHeight()-1);
            isUsingRedPotion = false;
            inv.redPotions--;
        } else if (isUsingBluePotion) {
            score += grid.destroyLine(y);
            isUsingBluePotion = false;
            inv.bluePotions--;
        } else if (isUsingGreenPotion) {
            score += grid.destroySquare(x-1, y-1);
            isUsingGreenPotion = false;
            inv.greenPotions--;
        } else{
            score += grid.searchAndDestroyAdjacentBlocks(x, y, grid.getBlock(x,y).getType(), true);
        }
        window.updateMouseIcon("");
    }

    public void inputText() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(c)liquer, (pr)ouge, (pb)pbleues, (pv)ertes :");
        String action = scanner.nextLine();
        int x;
        int y;
        switch (action) {
            case "c":
                System.out.print("Coordonnées de la case (\"x,y\"): ");
                try {
                    String coords = scanner.nextLine();
                    String[] coordsArray = coords.split(",");
                    x = Integer.parseInt(coordsArray[0], 16);
                    y = Integer.parseInt(coordsArray[1], 16);
                    if (hasScroll) {
                        if (!(scrollFirstLine <= y && y <= scrollLastLine))
                        return;
                    }
                    if (coordsArray.length == 2)
                        score += grid.searchAndDestroyAdjacentBlocks(x, y, grid.getBlock(x,y).getType(), true);
                }
                catch (Exception e) {
                }
                break;
            case "pr":
                if(inv.redPotions > 0) {
                    System.out.print("Colone: ");
                    String column = scanner.nextLine();
                    x = Integer.parseInt(column, 16);
                    x = Math.max(0, x);
                    x = Math.min(x, grid.getWidth()-1);
                    score += grid.destroyColumn(x, hasScroll ? scrollFirstLine : 0, hasScroll ? scrollLastLine : grid.getHeight()-1);
                    inv.redPotions--;
                } else {
                    System.out.println("Plus de potions rouges !");
                }
                break;
            case "pb":
                if(inv.bluePotions > 0) {
                    System.out.print("Line: ");
                    String column = scanner.nextLine();
                    y = Integer.parseInt(column, 16);
                    y = Math.max(scrollFirstLine, y);
                    y = Math.min(y, scrollLastLine);

                    score += grid.destroyLine(y);
                    inv.bluePotions--;
                } else {
                    System.out.println("Plus de potions bleues !");
                }
                break;
            case "pv":
                if(inv.greenPotions > 0) {
                    System.out.print("Ou jeter la potion verte? (\"x,y\"): ");
                    try {
                        String coords = scanner.nextLine();
                        String[] coordsArray = coords.split(",");
                        x = Integer.parseInt(coordsArray[0], 16);
                        y = Integer.parseInt(coordsArray[1], 16);
                        if (hasScroll) {
                            if (!(scrollFirstLine <= y && y <= scrollLastLine))
                            return;
                        }
                        if (coordsArray.length == 2)
                            score += grid.destroySquare(x-1, y-1);
                            inv.greenPotions--;
                    }
                    catch (Exception e) {
                    }
                }
                break;
            case "d":
                for(int i=0; i<grid.getWidth(); i++) {
                    score += grid.destroyColumn(i, hasScroll ? scrollFirstLine : 0, hasScroll ? scrollLastLine : grid.getHeight()-1);
                }
                break;
        }
    }

    private void gravityWithDisplay() {
        while(true) {
            delay();
            boolean state = grid.applyGravityStep();
            outputGraphics();
            if (state)
                break;
        }
    }

    private void gravityWithDisplayText() {
        while(true) {
            delay();
            boolean state = grid.applyGravityStep();
            outputText();
            if (state)
                break;
        }
    }

    public void play() {
        AudioManager.playSound(getMusicIdentifier(), true);
        boolean won = false;
        while (!won) {
            String boardString = grid.getBoardString();
            outputGraphics();
            inputGraphics();
            delay();
            window.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            while (true) {
                updateScroll();
                outputGraphics();
                delay();
                gravityWithDisplay();

                deliveredPackets += grid.removePacketsOnLastLine();

                delay();
                outputGraphics();
                delay();
                gravityWithDisplay();
                grid.shiftToLeft();
                gravityWithDisplay();
                delay();

                String boardString2 = grid.getBoardString();
                if(boardString2.equals(boardString)) {
                    break;
                }
                boardString = boardString2;
            }
            if(grid.isStuck()) {
                if (deliveredPackets >= packetGoal && score >= scoreGoal) {
                    won = true;
                } else {
                    break;
                }
            }
            window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if (won) {
            win();
        } else {
            lose();
        }
    }

    private void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {}
    }

    public void playText() {
        boolean won = false;
        while (!won) {
            String boardString = grid.getBoardString();
            outputText();
            inputText();
            while (true) {
                updateScroll();
                outputText();
                gravityWithDisplayText();

                deliveredPackets += grid.removePacketsOnLastLine();
                outputText();
                gravityWithDisplayText();

                grid.shiftToLeft();
                gravityWithDisplayText();

                updateScroll();

                String boardString2 = grid.getBoardString();
                if(boardString2.equals(boardString)) {
                    break;
                }
                boardString = boardString2;
            }
            if (deliveredPackets >= packetGoal && score >= scoreGoal) {
                won = true;
            } else if (grid.isStuck()) {
                break;
            }
        }
        if (won) {
            win();
        } else {
            lose();
        }
    }

    public void win() {
        unlockLevel();
        saveProgress();

        if (Launcher.GUI) {
            CustomButton nextLevelButton = new CustomButton("Next level", SpriteManager.getSprite("spr_button"), 200, 50, () -> {
                AudioManager.stopSound(getMusicIdentifier());
                changeLevel();
            });
            CustomButton levelMenuButton = new CustomButton("Back to menu", SpriteManager.getSprite("spr_button"), 200, 50, () -> {
                AudioManager.stopSound(getMusicIdentifier());
                menu.chooseLevel();
            });
            CustomPopup pop = new CustomPopup("You win!", nextLevelButton, levelMenuButton);


            pop.setVisible(true);
            window.getContentPane().add(pop, 0);
            window.revalidate();
            window.repaint();

            AudioManager.playSound("win", false);
        } else {
            System.out.println("You win!");
        }

    }

    public void lose() {
        saveProgress();

        if (Launcher.GUI) {
            CustomButton retryButton = new CustomButton("Retry?", SpriteManager.getSprite("spr_button"), 200, 50, () -> {
                AudioManager.stopSound(getMusicIdentifier());
                Thread thread = new Thread() {
                    public void run() {
                            menu.instanciateLevel(id);
                        }
                };
                thread.start();
            });
            CustomButton levelMenuButton = new CustomButton("Back to menu", SpriteManager.getSprite("spr_button"), 200, 50, () -> {
                AudioManager.stopSound(getMusicIdentifier());
                menu.chooseLevel();
            });
            CustomPopup pop = new CustomPopup("You lose!", retryButton, levelMenuButton);

            pop.setVisible(true);
            window.getContentPane().add(pop, 0);
            window.revalidate();
            window.repaint();

            AudioManager.playSound("lose", false);
        } else {
            System.out.println("You lose...");
        }
    }

    public void scroll(int length) {
        scrollFirstLine = Math.min(scrollFirstLine + length, grid.getHeight() - 11);
        scrollLastLine = Math.min(scrollLastLine + length, grid.getHeight() - 2);
        remainingLines = grid.getHeight()-scrollLastLine-2;
    }

    /**
    * Checks if the last line of the current scrolls has empty blocs. if yes, and there's remaining space, then
    * scroll the screen by that ammount of remaining space
    */
    public void updateScroll() {
        if (hasScroll)  {
            if (grid.lineHasEmptyBlocs(scrollLastLine - 1)) {
                if (grid.isLineFullyEmpty(scrollFirstLine)) {
                    int scrollLength = 1;
                    for(int line = 1; line<=scrollLastLine - 2; line++) {
                        if(scrollFirstLine+line >= grid.getHeight()) break;
                        if (grid.isLineFullyEmpty(scrollFirstLine+line))
                            scrollLength++;
                        else
                            break;
                    }
                    scroll(scrollLength);
                }
            }
        }
    }

    public void saveProgress() {
        try
        {
            PrintWriter writer = new PrintWriter("SavedGame.dat");
            writer.print("");
            writer.close();

            File saveFile = new File("SavedGame.dat");

            FileOutputStream savedFileOutputStream = new FileOutputStream(saveFile);
            ObjectOutputStream savedObjectOutputStream = new ObjectOutputStream(savedFileOutputStream);

            savedObjectOutputStream.writeObject(inv);
            savedObjectOutputStream.writeObject(prog);

            savedObjectOutputStream.close();
        }
        catch (Exception e)
        {
            System.out.println("Error saving progress");
        }
    }


}
