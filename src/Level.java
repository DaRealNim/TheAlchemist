import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class Level implements InputOutput, Game  {
    protected int packetGoal;
    protected int scoreGoal;
    protected int deliveredPackets;
    protected int score;
    protected Grid grid;

    public Level() {
        grid = new Grid(getGridStrings());
        deliveredPackets = 0;
        score = 0;
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
        grid.display();
        System.out.println();
        System.out.println("Packets delivered: "+deliveredPackets);
        System.out.println("Score            : "+score);
        System.out.println("\n");
    }

    public void inputGraphics() {}

    public void inputText() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(c)liquer sur un bloc ou utiliser une (f)usée?: ");
        String action = scanner.nextLine();
        switch (action) {
            case "c":
                System.out.print("Coordonnées de la case (\"x,y\"): ");
                String coords = scanner.nextLine();
                String[] coordsArray = coords.split(",");
                int x = Integer.parseInt(coordsArray[0], 16);
                int y = Integer.parseInt(coordsArray[1], 16);
                if (coordsArray.length == 2) {
                    grid.searchAndDestroyAdjacentBlocks(x, y, grid.getBlock(x,y).getType());
                }
        }
    }

    private void gravityWithDisplay() {
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
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
            outputText();
            inputText();
            while (true) {
                //when action is done, makes all blocks fall
                outputText();
                gravityWithDisplay();

                //deliver packets, apply gravity again
                deliveredPackets += grid.removePacketsOnLastLine();
                outputText();
                gravityWithDisplay();

                //shift to left, gravity again
                grid.shiftToLeft();
                gravityWithDisplay();

                //if the grid hasn't changed, we quit
                if(grid.getBoardString().equals(boardString))
                    break;
            }
            if (deliveredPackets >= packetGoal && score >= scoreGoal) {
                //WON
            }
        }
    }

}
