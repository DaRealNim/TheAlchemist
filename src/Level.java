import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.PrintWriter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

public abstract class Level implements InputOutput, Game  {
    protected int id;
    protected int packetGoal;
    protected int scoreGoal;
    protected int deliveredPackets;
    protected int score;
    protected int scrollFirstLine;
    protected int scrollLastLine;
    protected int remainingLines;
    protected boolean hasScroll;
    protected boolean isUsingRocketGUI;
    protected Grid grid;
    protected Window window;
    protected Progression prog;
    protected Inventory inv;
    protected Menu menu;

    public Level(Window window, Menu menu, Progression prog, Inventory inv, Integer id) {
        grid = new Grid(getGridStrings());
        deliveredPackets = 0;
        score = 0;
        isUsingRocketGUI = false;
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
        if (hasScroll) {
            scrollFirstLine = 0;
            scrollLastLine = 9;
            remainingLines = grid.getHeight() - scrollLastLine - 2;
        }
        this.prog = prog;
        this.inv = inv;
    }

    public abstract String[] getGridStrings();

    public abstract void changeLevel();

    public abstract void unlockLevel();

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
        // JButton rocketInternalButton = new JButton("Use rocket");
        CustomButton rocketButton = new CustomButton("Use rocket", () -> {
            isUsingRocketGUI = !isUsingRocketGUI;
            System.out.println(isUsingRocketGUI);
        });
        rocketButton.setLocation(600,10);
        window.add(rocketButton);
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
        // System.out.println(scrollFirstLine + ", "  + scrollLastLine);
        System.out.println("\n");
    }

    public void inputGraphics() {
        System.out.println("Waiting for block...");
        while(!grid.blockClicked) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {}
        }
        int x = grid.blockClickedX;
        int y = grid.blockClickedY;
        System.out.println("BLOCKCLICKED");
        // System.out.println(grid.getBlock(x,y).getType());
        if (isUsingRocketGUI) {
            score += grid.destroyColumn(x, hasScroll ? scrollFirstLine : 0, hasScroll ? scrollLastLine : grid.getHeight()-1);
            isUsingRocketGUI = false;
        } else {
            score += grid.searchAndDestroyAdjacentBlocks(x, y, grid.getBlock(x,y).getType(), true);
        }
        grid.blockClicked = false;
    }

    public void inputText() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(c)liquer sur un bloc ou utiliser une (f)usée?: ");
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
            case "f":
                System.out.print("Colone: ");
                String column = scanner.nextLine();
                x = Integer.parseInt(column, 16);
                score += grid.destroyColumn(x, hasScroll ? scrollFirstLine : 0, hasScroll ? scrollLastLine : grid.getHeight()-1);
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
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {}
            boolean state = grid.applyGravityStep();
            outputGraphics();
            if (state)
                break;
        }
    }

    private void gravityWithDisplayText() {
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {}
            boolean state = grid.applyGravityStep();
            outputText();
            if (state)
                break;
        }
    }

    public void play() {
        boolean won = false;
        while (!won) {
            String boardString = grid.getBoardString();
            outputGraphics();
            inputGraphics();

            while (true) {
                updateScroll();

                outputGraphics();
                gravityWithDisplay();

                deliveredPackets += grid.removePacketsOnLastLine();

                delay();
                outputGraphics();
                delay();
                gravityWithDisplay();

                grid.shiftToLeft();
                delay();
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
        }
        if (won) {
            System.out.println("GAGNE!!!!");
            win();
        } else {
            System.out.println("Perdu...");
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
        boolean loose = false;
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
            System.out.println("GAGNE!!!!");
            win();
        } else {
            System.out.println("Perdu...");
            lose();
        }
    }

    public void win() {
        unlockLevel();
        saveProgress();

        if (menu != null) {
            JButton nextLevelButton = new JButton("Next level");
            JButton levelMenuButton = new JButton("Back to level menu");
            CustomPopup pop = new CustomPopup("You win!", nextLevelButton, levelMenuButton);

            nextLevelButton.addActionListener((ActionEvent e) -> {
                pop.setVisible(false);
                changeLevel();
                window.repaint();
            });

            levelMenuButton.addActionListener((ActionEvent e) -> {
                pop.setVisible(false);
                menu.chooseLevel();
                window.repaint();
            });

            pop.setVisible(true);
            window.getContentPane().add(pop, 0);
            window.revalidate();
            window.repaint();
        }

    }

    public void lose() {
        saveProgress();

        if (menu != null) {
            JButton retryButton = new JButton("Retry?");
            JButton levelMenuButton = new JButton("Back to level menu");
            CustomPopup pop = new CustomPopup("You lose!", retryButton, levelMenuButton);

            // JPopupMenu losePopup = new JPopupMenu("Lost!");

            retryButton.addActionListener((ActionEvent e) -> {
                pop.setVisible(false);
                Thread thread = new Thread() {
                    public void run() {
                            menu.instanciateLevel(id);
                        }
                };
                thread.start();
            });

            levelMenuButton.addActionListener((ActionEvent e) -> {
                pop.setVisible(false);
                menu.chooseLevel();
                window.repaint();
            });

            pop.setVisible(true);
            window.getContentPane().add(pop, 0);
            window.revalidate();
            window.repaint();

            // losePopup.add(retryButton);
            // losePopup.add(levelMenuButton);
            //
            // losePopup.show(window, 200, 300);
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
                    // if (scrollFirstLine >= grid.getHeight()-10-1) {
                    //     scrollLastLine = grid.getHeight()-1;
                    //     remainingLines = 0;
                    // } else {
                    for(int line = 1; line<=scrollLastLine - 2; line++) {
                        if (grid.isLineFullyEmpty(scrollFirstLine+line))
                            scrollLength++;
                        else
                            break;
                    }
                    scroll(scrollLength);
                    // }
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
