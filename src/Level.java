import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class Level implements InputOutput, Game  {
    protected int packetGoal;
    protected int scoreGoal;
    protected Grid grid;

    public Level() {
        grid = new Grid(getGridStrings());
    }

    public abstract String[] getGridStrings();

    public abstract void changeLevel();

    public abstract void unlockLevel();

    public static void clearTerminal() {
       System.out.print("\033[H\033[2J");
       System.out.flush();
    }

    public void displayGraphics() {}

    public void displayText() {
        clearTerminal();
        grid.display();
        System.out.println("\n");
    }

    public void actionGraphics() {}

    public void actionText() {
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
            displayText();
            if (state) break;
        }
    }

    // private void shiftWithDisplay() {
    //     while(true) {
    //         try {
    //             TimeUnit.MILLISECONDS.sleep(500);
    //         } catch (InterruptedException e) {}
    //         boolean state = grid.shiftToLeft();
    //         displayText();
    //         if (state) break;
    //     }
    // }

    public void play() {
        boolean won = false;
        while(!won) {
            displayText();
            actionText();

            //when action is done, makes all blocks fall
            displayText();
            gravityWithDisplay();
            //shift to left
            grid.shiftToLeft();
            //gravity again
            gravityWithDisplay();
        }
    }

}
