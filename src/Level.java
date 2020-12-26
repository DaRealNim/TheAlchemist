import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class Level implements InputOutput, Game  {
    protected int packetGoal;
    protected int scoreGoal;
    protected int deliveredPackets;
    protected int score;
    protected int scrollFirstLine;
    protected int scrollLastLine;
    protected int remainingLines;
    protected boolean hasScroll;
    protected Grid grid;

    public Level() {
        grid = new Grid(getGridStrings());
        deliveredPackets = 0;
        score = 0;
        hasScroll = (grid.getHeight() > 10 );
        if (hasScroll) {
            scrollFirstLine = 0;
            scrollLastLine = 9;
            remainingLines = grid.getHeight() - scrollLastLine - 2;
        }
    }

    public abstract String[] getGridStrings();

    public abstract void changeLevel();

    public abstract void unlockLevel();

    public static void clearTerminal() {
       System.out.print("\033[H\033[2J");
       System.out.flush();
    }

    public void outputGraphics() {}

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

    public void inputGraphics() {}

    public void inputText() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(c)liquer sur un bloc ou utiliser une (f)usée?: ");
        String action = scanner.nextLine();
        int x;
        int y;
        switch (action) {
            case "c":
                System.out.print("Coordonnées de la case (\"x,y\"): ");
                String coords = scanner.nextLine();
                String[] coordsArray = coords.split(",");
                x = Integer.parseInt(coordsArray[0], 16);
                y = Integer.parseInt(coordsArray[1], 16);
                if (hasScroll) {
                    if (!(scrollFirstLine <= y && y <= scrollLastLine))
                        return;
                }
                if (coordsArray.length == 2) {
                    score += grid.searchAndDestroyAdjacentBlocks(x, y, grid.getBlock(x,y).getType());
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
            outputText();
            if (state) break;
        }
    }

    // private void shiftWithDisplay() {
    //     while(true) {
    //         try {
    //             TimeUnit.MILLISECONDS.sleep(500);
    //         } catch (InterruptedException e) {}
    //         boolean state = grid.shiftToLeft();
    //         outputText();
    //         if (state) break;
    //     }
    // }

    public void play() {
        boolean won = false;
        while (!won) {
            String boardString = grid.getBoardString();
            System.out.println(boardString);
            outputText();
            inputText();
            while (true) {
                // System.out.println("updateScroll");
                updateScroll();

                //when action is done, makes all blocks fall
                // System.out.println("gravity");
                outputText();
                gravityWithDisplay();

                // System.out.println("deliver");
                //deliver packets, apply gravity again
                deliveredPackets += grid.removePacketsOnLastLine();
                outputText();
                gravityWithDisplay();

                // System.out.println("shift");
                //shift to left, gravity again
                grid.shiftToLeft();
                gravityWithDisplay();

                // System.out.println("updateScroll");
                updateScroll();

                //if the grid hasn't changed, we quit
                String boardString2 = grid.getBoardString();
                if(boardString2.equals(boardString)) {
                    break;
                }
                boardString = boardString2;
            }
            if (deliveredPackets >= packetGoal && score >= scoreGoal) {
                won = true;
            }
        }
        System.out.println("GAGNE!!!!");
    }

    public void scroll(int length) {
        scrollFirstLine = Math.min(scrollFirstLine+length, grid.getHeight()-10-1);
        scrollLastLine = Math.min(scrollLastLine+length, grid.getHeight()-2);
        remainingLines = grid.getHeight()-scrollLastLine-2;
    }

    /**
     * Checks if the last line of the current scrolls has empty blocs. if yes, and there's remaining space, then
     * scroll the screen by that ammount of remaining space
     */
    public void updateScroll() {
        if (hasScroll)  {
            if (grid.lineHasEmptyBlocs(scrollLastLine-1)) {
                if (grid.isLineFullyEmpty(scrollFirstLine)) {
                    int scrollLength = 1;
                    // if (scrollFirstLine >= grid.getHeight()-10-1) {
                    //     scrollLastLine = grid.getHeight()-1;
                    //     remainingLines = 0;
                    // } else {
                        for(int line=1; line<=scrollLastLine-2; line++) {
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



}
